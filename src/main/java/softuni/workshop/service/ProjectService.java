package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProjectService {

    void importProjects() throws JAXBException;

    boolean areImported();

    String readProjectsXmlFile() throws IOException;

    String exportFinishedProjects();

    void exportProjectToJson() throws IOException;

    String readProjectJsonFile() throws IOException;

    boolean areExported() throws IOException;

    String exportProjectsWithNameEnding();
}
