package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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

    private Util() {
    }

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

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration()
                    .configure()  // Загружает настройки из hibernate.cfg.xml
                    .addAnnotatedClass(User.class); // Регистрируем сущность User

            logger.log(Level.INFO, "Building Hibernate SessionFactory");
            return configuration.buildSessionFactory();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Initial SessionFactory creation failed.", e);
        }
        return null;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

