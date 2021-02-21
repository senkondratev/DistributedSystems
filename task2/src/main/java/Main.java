import db.DBConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws Exception{
        DBConnection.init();

        logger.info("Start");
        System.out.println("Hello, world!");
        logger.info("End");
        xml.OSMReader reader = new xml.OSMReader();
        reader.readFile();

        DBConnection.closeConnection();
    }
}
