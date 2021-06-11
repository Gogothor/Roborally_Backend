package com.example.demo.dal.implementations;

import com.example.demo.dal.interfaces.IUserDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class UserDao implements IUserDao {
    static final HashMap<Integer, User> users = new HashMap<>();
    static final HashMap<String, Integer> ids = new HashMap<>();
    static private int userIDCounter = 0;

    @Override
    public User getUser(int userID) throws DaoException {
        return users.get(userID);
    }

    @Override
    public User getUser(String username) throws DaoException {
        return users.get(ids.get(username));
    }

    @Override
    public int createUser(User user) throws DaoException {
        userIDCounter++;
        user.setUserID(userIDCounter);
        users.put(user.getUserID(), user);
        ids.put(user.getUserName(), user.getUserID());
        return userIDCounter;
    }

}
