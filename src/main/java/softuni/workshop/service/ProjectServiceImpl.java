package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.ProjectDto;
import softuni.workshop.domain.dtos.importDto.ProjectRootDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_XML_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\xmls\\projects.xml";

    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final CompanyRepository companyRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper, FileUtil fileUtil, ValidatorUtil validatorUtil, CompanyRepository companyRepository) {
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.companyRepository = companyRepository;
    }

    @Override
    public void importProjects() throws JAXBException {

        ProjectRootDto projectRootDto = this.xmlParser.importXMl(ProjectRootDto.class,PROJECT_XML_FILE_PATH);

        for (ProjectDto projectDto : projectRootDto.getProjectDtos()) {
            Project project = this.modelMapper.map(projectDto, Project.class);
            if (!this.validatorUtil.isValid(project)){
                this.validatorUtil.violations(project).forEach(v -> System.out.println(v.getMessage()));

                continue;
            }
            Company company = this.companyRepository.findCompanyByName(projectDto.getCompany().getName());
            project.setCompany(company);

            this.projectRepository.saveAndFlush(project);
        }
    }


    @Override
    public boolean areImported() {
        return this.projectRepository.count() > 0;
    }

    @Override
    public String readProjectsXmlFile() throws IOException {
        return this.fileUtil.readFile(PROJECT_XML_FILE_PATH);
    }

    @Override
    public String exportFinishedProjects(){
        StringBuilder sb = new StringBuilder();

        List<Project> projects = this.projectRepository.findAllByFinishedIsTrue();

        for (Project project : projects) {
            sb.append(String.format("Project Name: %s",project.getName())).append(System.lineSeparator())
                    .append(String.format("    Description: %s",project.getDescription())).append(System.lineSeparator())
                    .append(String.format("    Payment: %s",project.getPayment())).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
