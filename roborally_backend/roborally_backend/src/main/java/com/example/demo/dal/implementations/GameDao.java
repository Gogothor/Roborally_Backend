package com.example.demo.dal.implementations;

import com.example.demo.dal.interfaces.IGameDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class GameDao implements IGameDao {
    static final HashMap<Integer, Game> games = new HashMap<>();
    static private int gameIDCounter = 0;

    @Override
    public Game getGame(int gameID) throws DaoException {
        return games.get(gameID);
    }

    @Override
    public int createGame(Game game) throws DaoException {
        gameIDCounter++;
        game.setGameID(gameIDCounter);
        games.put(game.getGameID(), game);
        return gameIDCounter;
    }

    @Override
    public void updateGame(Game game, int gameID) throws DaoException {
    games.replace(gameID, game);
    }

    @Override
    public void deleteGame(int gameID) throws DaoException {
    games.remove(gameID);
    }

    @Override
    public Game[] getGameList() {
        return games.values().toArray(new Game[0]);
    }
}
