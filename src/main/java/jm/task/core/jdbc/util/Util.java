package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {
    private static final Logger logger = Logger.getLogger(Util.class.getName());
    private static final String URL = "jdbc:mysql://localhost:3306/pp_1_1_3";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "0000";

    private Util (){}

    public static Connection establishConnection() {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Database connection established successfully");
            return connection;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to establish database connection", e);
        }
        return null;
    }
}
