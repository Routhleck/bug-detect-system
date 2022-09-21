package com.demo.service;


import com.demo.dao.UserDao;
import com.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User selectUser(String username, String password){

        User user = userDao.findByUsernameAndPassword(username, password);

        return user;
    }

    public boolean updatePassword(String username,
                               String newPassword){
        boolean b = userDao.updatePassword(username, newPassword);
        return b;
    }

    public boolean insertUser(String username,
                              String password){
        boolean b = userDao.insertUser(username, password);
        return b;
    }

    public boolean deleteUser(String username){
        boolean b = userDao.deleteUser(username);
        return b;
    }
}
