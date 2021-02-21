package xml;

import dao.NodeDaoImpl;
import model.NodeEntity;
import model.generated.Node;

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

public class OSMReader {
    public void readFile() throws XMLStreamException, IOException, JAXBException, SQLException {
        try (XmlStreamProcessor processor = new XmlStreamProcessor(Files.newInputStream(Paths.get("RU-NVS.osm")))) {
            JAXBContext jaxbContext = JAXBContext.newInstance(Node.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XMLStreamReader reader = processor.getReader();
            NodeDaoImpl nodeDao = new NodeDaoImpl();
            while (reader.hasNext()) {
                int event = reader.next();
                if(event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                    Node node = (Node) unmarshaller.unmarshal(reader);
                    nodeDao.insertNode(NodeEntity.convert(node));
                }
            }
        }
    }
}