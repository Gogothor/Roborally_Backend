package com.example.demo.dal.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.model.User;


public interface IUserDao {
    User getUser(int userID) throws DaoException;
    User getUser(String username) throws DaoException;
    int createUser(User user) throws DaoException;
}
