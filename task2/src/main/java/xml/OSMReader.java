package xml;

import dao.NodeDaoImpl;
import dao.TagDao;
import dao.TagDaoImpl;
import db.NodeProcessor;
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
            TagDaoImpl tagDao = new TagDaoImpl();
            NodeProcessor nodeProcessor = new NodeProcessor(nodeDao, tagDao);
            int i = 0;
            Long sum = 0L;
            while (reader.hasNext()) {
                int event = reader.next();
                if(event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())){
                    Node node = (Node) unmarshaller.unmarshal(reader);
                    NodeEntity nodeEntity = NodeEntity.convert(node);
                    long cur = System.currentTimeMillis();
                    nodeProcessor.putNodeBuffered(nodeEntity);
                    cur =  System.currentTimeMillis() - cur;
                    sum+=cur;
                    if (i == 100000){
                        break;
                    }
                    i++;
                }
            }
            System.out.println("Всего секунд на вставку "+ sum/1000);
            double perOne = (sum.doubleValue()/(100000));
            System.out.println("Миллисекунд на объект " + perOne);
            System.out.println("Объектов в секунду " + 1.0/perOne*1000);
        }
    }
}
