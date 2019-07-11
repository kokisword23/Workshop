package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.CompanyService;
import softuni.workshop.service.EmployeeService;
import softuni.workshop.service.ProjectService;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {

    private final ProjectService projectService;
    private final CompanyService companyService;
    private final EmployeeService employeeService;

    @Autowired
    public ImportController(ProjectService projectService, CompanyService companyService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.companyService = companyService;
        this.employeeService = employeeService;
    }

    @GetMapping("/xml")
    public ModelAndView importXml(ModelAndView modelAndView) {
        boolean[] areImported = new boolean[] {
                this.companyService.areImported(),
                this.projectService.areImported(),
                this.employeeService.areImported()
        };
        modelAndView.addObject("areImported", areImported);
        return super.view("xml/import-xml",modelAndView);
    }

    @GetMapping("/companies")
    public ModelAndView importOrders(ModelAndView modelAndView) throws IOException {
        String companiesXmlFileContent = this.companyService.readCompaniesXmlFile();

        modelAndView.addObject("companies",companiesXmlFileContent);
        return super.view("xml/import-companies",modelAndView);
    }

    @PostMapping("/companies")
    public ModelAndView importOrdersConfirm() throws JAXBException{
        this.companyService.importCompanies();

        return super.redirect("/import/xml");
    }

    @GetMapping("/projects")
    public ModelAndView importProjects(ModelAndView modelAndView) throws IOException {
        String projectsXmlFileContent = this.projectService.readProjectsXmlFile();

        modelAndView.addObject("projects",projectsXmlFileContent);
        return super.view("xml/import-projects",modelAndView);
    }

    @PostMapping("/projects")
    public ModelAndView importProjectsConfirm() throws JAXBException{
        this.projectService.importProjects();

        return super.redirect("/import/xml");
    }

    @GetMapping("/employees")
    public ModelAndView importEmployees(ModelAndView modelAndView) throws IOException {
        String employeesXmlFileContent = this.projectService.readProjectsXmlFile();

        modelAndView.addObject("employees",employeesXmlFileContent);
        return super.view("xml/import-employees",modelAndView);
    }

    @PostMapping("/employees")
    public ModelAndView importEmployeesConfirm() throws JAXBException{
        this.employeeService.importEmployees();

        return super.redirect("/import/xml");
    }
}
