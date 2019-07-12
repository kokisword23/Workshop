package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.ProjectDto;
import softuni.workshop.domain.dtos.importDto.ProjectRootDto;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.ProjectJsonDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final static String PROJECT_XML_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\xmls\\projects.xml";
    private final static String JSON_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\jsons\\projects.json";

    private final ProjectRepository projectRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final CompanyRepository companyRepository;
    private final Gson gson;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, XmlParser xmlParser, ModelMapper modelMapper,
                              FileUtil fileUtil, ValidatorUtil validatorUtil, CompanyRepository companyRepository, Gson gson) {
        this.projectRepository = projectRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.companyRepository = companyRepository;
        this.gson = gson;
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


    @Override
    public void exportProjectToJson() throws IOException {
        List<ProjectJsonDto> projects = this.projectRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProjectJsonDto.class))
                .collect(Collectors.toList());
        FileWriter fileWriter = new FileWriter(new File(JSON_FILE_PATH));
        String content = this.gson.toJson(projects);
        fileWriter.write(content);

        fileWriter.close();
    }

    @Override
    public String readProjectJsonFile() throws IOException {
        return this.fileUtil.readFile(JSON_FILE_PATH);
    }

    @Override
    public boolean areExported() throws IOException {
        return this.readProjectJsonFile().length() > 0;
    }

    @Override
    public String exportProjectsWithNameEnding() {
        StringBuilder sb = new StringBuilder();

        List<Project> projects = this.projectRepository.findAllByNameEndingWithOrderByPaymentDesc("e");

        for (Project project : projects) {
            sb.append(String.format("Project name: %s",project.getName())).append(System.lineSeparator())
                    .append(String.format("\tPayment: %s",project.getPayment())).append(System.lineSeparator())
                    .append(String.format("\tStart date: %s",project.getStartDate())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
