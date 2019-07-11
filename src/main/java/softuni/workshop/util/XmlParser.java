package softuni.workshop.util;

import javax.xml.bind.JAXBException;

public interface XmlParser {

    <O> O importXMl(Class<O> objectClass,String path) throws JAXBException;

    <O> void exportXML(O entity,String path) throws JAXBException;
}
