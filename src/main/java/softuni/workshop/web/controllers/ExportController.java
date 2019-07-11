package softuni.workshop.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.EmployeeService;
import softuni.workshop.service.ProjectService;

@Controller
@RequestMapping("/export")
public class ExportController extends BaseController {

    private final ProjectService projectService;
    private final EmployeeService employeeService;

    @Autowired
    public ExportController(ProjectService projectService, EmployeeService employeeService) {
        this.projectService = projectService;
        this.employeeService = employeeService;
    }

    @GetMapping("/project-if-finished")
    public ModelAndView exportProjectsIfFinished(ModelAndView modelAndView) {
        String exportResult = this.projectService.exportFinishedProjects();

        modelAndView.addObject("projectsIfFinished",exportResult);
        return super.view("export/export-project-if-finished",modelAndView);
    }

    @GetMapping("/employees-above")
    public ModelAndView export(ModelAndView modelAndView) {
        String exportResult = this.employeeService.exportEmployeesWithAgeAbove();

        modelAndView.addObject("employeesAbove",exportResult);
        return super.view("export/export-employees-with-age",modelAndView);
    }

}
