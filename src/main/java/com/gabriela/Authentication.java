package com.gabriela;

import com.gabriela.dao.UserDao;
import com.gabriela.model.User;

public class Authentication {

    private final UserDao userDao;

    public Authentication() {
        userDao = new UserDao();
    }

    public boolean register(String username, String password, String role) {
        return userDao.registerUser(username, password, role);
    }

    public User login(String username, String password) {
        return userDao.loginUser(username, password);
    }
}