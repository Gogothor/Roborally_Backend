package com.example.demo.service.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Game;
import com.example.demo.model.Player;
import com.example.demo.model.User;

import java.util.List;


public interface IGameService {
    Board getBoard(int boardId) throws ServiceException, DaoException;

    Board[] getBoardList() throws ServiceException, DaoException;

    Game[] getGameList() throws ServiceException, DaoException;

    int createGame(Integer numOfPlayers, String boardChoice) throws ServiceException, DaoException;

    int saveBoard(Board board) throws ServiceException, DaoException;

    Player getCurrentPlayer(int boardId) throws ServiceException, DaoException;

    void setCurrentPlayer(int boardId, int playerId) throws ServiceException, DaoException;

    int addPlayer(int boardId, Player player) throws ServiceException, DaoException;

    void moveCurrentPlayer(int boardId, int x, int y) throws ServiceException, DaoException;

    void movePlayer(Board board, int x, int y, int playerId) throws DaoException;

    void switchCurrentPlayer(int boardId) throws ServiceException, DaoException;

    User createUser(String username) throws ServiceException, DaoException;

    User getUser(int userID) throws ServiceException, DaoException;

    User getUser(String username) throws ServiceException, DaoException;

    boolean joinGame(int gameID, User user) throws ServiceException, DaoException;
}
