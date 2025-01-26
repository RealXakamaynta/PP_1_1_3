package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Maria", "Botvinskaya", (byte) 19);
        userService.saveUser("Maria", "Zolotuhina", (byte) 20);
        userService.saveUser("123", "456", (byte) 1);
        userService.saveUser("Name", "LASTNAME", (byte) 100);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
