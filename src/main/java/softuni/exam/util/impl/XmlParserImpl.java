package softuni.exam.util.impl;

import softuni.exam.util.XmlParser;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser {

    private JAXBContext context;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException {
        context = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new FileReader(filePath));
    }

    @Override
    public <T> void writeToFile(String filePath, T entity) throws JAXBException {
        context = JAXBContext.newInstance(entity.getClass());

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(entity, new File(filePath));

    }
}
