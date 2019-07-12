package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CompanyService {

    void importCompanies() throws JAXBException;

    boolean areImported();

    String readCompaniesXmlFile() throws IOException;

    void exportJsonCompanies() throws IOException;

    String readCompaniesJsonFile() throws IOException;

    boolean areExported() throws IOException;
}
