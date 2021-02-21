package db;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static final String URL = "jdbc:postgresql://localhost:5432/OsmBase";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "123";

    private static Connection connection;

    public static void init() throws Exception {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File file = new File(classLoader.getResource("initDB.sql").getFile());
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            StringBuffer stringBuffer = new StringBuffer("");
            while (reader.ready()){
                stringBuffer.append(reader.readLine());
            }
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute(new String(stringBuffer));
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
