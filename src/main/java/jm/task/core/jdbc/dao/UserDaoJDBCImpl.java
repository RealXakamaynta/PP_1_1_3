package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection;
    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    public UserDaoJDBCImpl() {
        this.connection = Util.establishConnection();
    }

    @Override
    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS userstable (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(255) NOT NULL,
                        lastName VARCHAR(255) NOT NULL,
                        age TINYINT NOT NULL
                    )""");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create table", e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS userstable");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to drop table", e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE userstable");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to clean table", e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = String.format("INSERT INTO userstable (name, lastName, age) VALUES ('%s', '%s', %d)",
                name, lastName, age);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            logger.info(String.format("User с именем – %s добавлен в базу данных", name));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to save user", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM userstable WHERE id = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to remove user", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM userstable")) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get users list", e);
        }
        return users;
    }
}