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

import java.io.IOException;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;
    private final CompanyService companyService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService, CompanyService companyService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
        this.companyService = companyService;
    }

    @GetMapping("/json")
    public ModelAndView importXml(ModelAndView modelAndView) throws IOException {
        boolean[] areExported = new boolean[] {
                this.companyService.areExported(),
                this.projectService.areExported(),
                this.employeeService.areExported()
        };
        modelAndView.addObject("areExported", areExported);
        return super.view("json/export-json",modelAndView);
    }

    @GetMapping("/project-if-finished")
    public ModelAndView exportProjectsIfFinished(ModelAndView modelAndView) {
        String exportResult = this.projectService.exportFinishedProjects();

        modelAndView.addObject("projectsIfFinished",exportResult);
        return super.view("export/export-project-if-finished",modelAndView);
    }

    @GetMapping("/employees-above")
    public ModelAndView exportEmployeesAbove25Years(ModelAndView modelAndView){
        String exportResult = this.employeeService.exportEmployeesWithAgeAbove();

        modelAndView.addObject("employeesAbove",exportResult);
        return super.view("export/export-employees-with-age",modelAndView);
    }

    @GetMapping("/employees-with-name")
    public ModelAndView exportEmployeesWithGivenName(ModelAndView modelAndView){
        String content = this.employeeService.exportEmployeesWithGivenName();

        modelAndView.addObject("employeesWithName", content);
        return super.view("export/export-employees-with-name", modelAndView);
    }

    @GetMapping("/employees-with-project")
    public ModelAndView exportEmployeesWithGivenProjectName(ModelAndView modelAndView){
        String content = this.employeeService.exportEmployeesWithGivenProjectName();

        modelAndView.addObject("employeeWithProjectName", content);
        return super.view("export/export-employees-with-project", modelAndView);
    }

    @GetMapping("/projects-with-name-ending")
    public ModelAndView exportProjectsWithNameEnding(ModelAndView modelAndView){
        String content = this.projectService.exportProjectsWithNameEnding();

        modelAndView.addObject("projectsWithNameEnding", content);
        return super.view("export/export-projects-with-name-ending", modelAndView);
    }

    @GetMapping("/companies")
    public ModelAndView exportCompanies(){
        return super.view("json/export-companies");
    }

    @PostMapping("/companies")
    public ModelAndView confirmExportCompanies() throws IOException {
        this.companyService.exportJsonCompanies();

        return super.redirect("/export/json");
    }


    @GetMapping("/projects")
    public ModelAndView exportProjects(){
        return super.view("json/export-projects");
    }

    @PostMapping("/projects")
    public ModelAndView confirmExportProjects() throws IOException {
        this.projectService.exportProjectToJson();

        return super.redirect("/export/json");
    }


    @GetMapping("/employees")
    public ModelAndView exportEmployees(ModelAndView modelAndView){

        return super.view("json/export-employees");
    }

    @PostMapping("/employees")
    public ModelAndView confirmExportEmployees() throws IOException {
        this.employeeService.exportEmployeesToJson();

        return super.redirect("/export/json");
    }
}
