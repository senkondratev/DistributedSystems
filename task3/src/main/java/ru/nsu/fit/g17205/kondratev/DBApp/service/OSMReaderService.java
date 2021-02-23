package ru.nsu.fit.g17205.kondratev.DBApp.service;

import ru.nsu.fit.g17205.kondratev.DBApp.model.NodeEntity;
import ru.nsu.fit.g17205.kondratev.DBApp.model.generated.Node;
import ru.nsu.fit.g17205.kondratev.DBApp.xml.XmlStreamProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

@Service
public class OSMReaderService {
    @Autowired
    NodeService nodeProcessor;
    public void readFile() throws XMLStreamException, IOException, JAXBException, SQLException {
        try (XmlStreamProcessor processor = new XmlStreamProcessor(Files.newInputStream(Paths.get("RU-NVS.osm")))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XMLStreamReader reader = processor.getReader();
            int i = 0;
            while (reader.hasNext()) {
                int event = reader.next();
                if(event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                    Node node = (Node) unmarshaller.unmarshal(reader);
                    NodeEntity nodeEntity = NodeEntity.convert(node);
                    nodeProcessor.save(nodeEntity);
                    if (i == 10000){
                        break;

                    }
                    i++;
                }
            }

        }
    }
}
