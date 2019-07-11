package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EmployeeService {

    void importEmployees() throws JAXBException;

    boolean areImported();

    String readEmployeesXmlFile() throws IOException;

    String exportEmployeesWithAgeAbove();

    void exportEmployees() throws JAXBException;
}
