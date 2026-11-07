
package com.gabriela;

import com.gabriela.dao.UserDao;
import com.gabriela.ui.AuthMenu;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the Custom Flower Bouquet System!");

        UserDao userDao = new UserDao();

        userDao.createUsersTable();

        AuthMenu.showMenu();
    }
}
