package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.exportDto.EmployeeExportDto;
import softuni.workshop.domain.dtos.exportDto.EmployeeExportRootDto;
import softuni.workshop.domain.dtos.importDto.EmployeeDto;
import softuni.workshop.domain.dtos.importDto.EmployeeRootDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.swing.text.html.parser.Entity;
import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_XML_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\xmls\\employees.xml";
    private final static String EXPORT_EMPLOYEES_FILE_PATH =
            "C:\\SoftUni\\DB\\workshop-skeleton\\src\\main\\resources\\files\\xmls\\exported-employees.xml";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, FileUtil fileUtil, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public void importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = this.xmlParser.importXMl(EmployeeRootDto.class,EMPLOYEE_XML_FILE_PATH);

        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtos()) {
            Employee employee = this.modelMapper.map(employeeDto,Employee.class);
            if (!this.validatorUtil.isValid(employee)){
                this.validatorUtil.violations(employee).forEach(v-> System.out.println(v.getMessage()));

                continue;
            }

            Project project = this.projectRepository.findProjectByName(employeeDto.getProjectDto().getName());
            employee.setProject(project);

            this.employeeRepository.saveAndFlush(employee);
        }
    }


    @Override
    public boolean areImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_XML_FILE_PATH);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        StringBuilder sb = new StringBuilder();

        List<Employee> employees = this.employeeRepository.findAllByAgeGreaterThan(25);

        for (Employee employee : employees) {
            sb.append(String.format(" Name: %s %s",employee.getFirstName(), employee.getLastName())).append(System.lineSeparator())
                    .append(String.format("    Age: %d",employee.getAge())).append(System.lineSeparator())
                    .append(String.format("    Project name: %s",employee.getProject().getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public void exportEmployees() throws JAXBException {
        List<EmployeeExportDto> exportDtos =
                this.employeeRepository.findAll()
                .stream()
                .map(e -> this.modelMapper.map(e,EmployeeExportDto.class))
                .collect(Collectors.toList());

        EmployeeExportRootDto export = new EmployeeExportRootDto();
        export.setEmployeeExportDtos(exportDtos);
        this.xmlParser.exportXML(export,EXPORT_EMPLOYEES_FILE_PATH);
    }
}
