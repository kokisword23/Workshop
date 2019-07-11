package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface ProjectService {

    void importProjects() throws JAXBException;

    boolean areImported();

    String readProjectsXmlFile() throws IOException;

    String exportFinishedProjects();

}
