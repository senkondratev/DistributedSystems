import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OSMReader {
    public void readFile() throws XMLStreamException, IOException {
        Map<String, Integer> userChanges = new HashMap();
        Map<String, Integer> nodesPerKey = new HashMap();
        try (StaxStreamProcessor processor = new StaxStreamProcessor(Files.newInputStream(Paths.get("RU-NVS.osm")))) {
            boolean inside = false;
            XMLStreamReader reader = processor.getReader();
            while (reader.hasNext()) {
                int event = reader.next();
                if(event == XMLEvent.START_ELEMENT && "tag".equals(reader.getLocalName()) && inside){
                    String key = reader.getAttributeValue(0);
                    if (!nodesPerKey.containsKey(key)){
                        nodesPerKey.put(key,1);
                    }
                    else{
                        nodesPerKey.put(key, nodesPerKey.get(key)+1);
                    }
                }
                if (event == XMLEvent.START_ELEMENT && "node".equals(reader.getLocalName())) {
                    inside = true;
                    String name = reader.getAttributeValue(4);
                    if (!userChanges.containsKey(name)){
                        userChanges.put(name,1);
                    }
                    else{
                        userChanges.put(name, userChanges.get(name)+1);
                    }
                }
                if (event == XMLEvent.END_ELEMENT && "node".equals(reader.getLocalName())){
                    inside = false;
                }
            }

            //Задание 1 - число изменений по пользователям
            Stream<Map.Entry<String, Integer>> stream = userChanges.entrySet().stream();
            userChanges = stream.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));
            userChanges.forEach((key, value) -> System.out.printf("%s %d %n", key, value));

            System.out.println("---------------------------------");

            //Задание 2 - число node, включающих key
            stream = nodesPerKey.entrySet().stream();
            nodesPerKey = stream.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2) -> e1, LinkedHashMap::new));
            nodesPerKey.forEach((key, value) -> System.out.printf("%s %d %n", key, value));
        }
    }
}
