package com.example.demo.dal.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.model.Game;

public interface IGameDao {
    Game getGame(int gameID) throws DaoException;

    int createGame(Game game) throws DaoException;

    void updateGame(Game game, int gameID) throws DaoException;

    void deleteGame(int gameID) throws DaoException;

    Game[] getGameList();
}
