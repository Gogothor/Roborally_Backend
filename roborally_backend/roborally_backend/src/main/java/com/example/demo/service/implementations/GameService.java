package com.example.demo.service.implementations;

import com.example.demo.controller.GameController.UserDto;
import com.example.demo.dal.interfaces.*;
import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.*;
import com.example.demo.service.interfaces.IGameService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GameService implements IGameService {
    private final IGameDao gameDao;
    private final IBoardDao boardDao;
    private final ISpaceDao spaceDao;
    private final IPlayerDao playerDao;
    private final IUserDao userDao;
    private final Board[] boards2 = {
            new Board(8, 8, "default")
    };

    static final Map<String, Board> boards = Map.of(
            "board 1", new Board(8, 8, "board 1"),
            "board 2", new Board(8, 12, "board 2"),
            "board 3", new Board(8, 16, "board 3")
    );

    public GameService(IGameDao gameDao, IBoardDao boardDao, ISpaceDao spaceDao, IPlayerDao playerDao, IUserDao userDao) {
        this.boardDao = boardDao;
        this.spaceDao = spaceDao;
        this.playerDao = playerDao;
        this.gameDao = gameDao;
        this.userDao = userDao;
    }

    @Override
    public Board getBoard(int boardId) throws ServiceException, DaoException {
        if (boardId < 0) {
            throw new ServiceException("Invalid board id " + boardId, HttpStatus.BAD_REQUEST);
        }
        Board board = boardDao.getBoard(boardId);
        if (board == null) {
            throw new ServiceException("No board found with board id " + boardId, HttpStatus.NOT_FOUND);
        }

        return board;
    }

    @Override
    public Board[] getBoardList() {
        return boardDao.getBoardList();
    }

    @Override
    public Game[] getGameList() {
        return gameDao.getGameList();
    }

    @Override
    public int createGame(Integer numOfPlayers, String boardChoice) throws ServiceException, DaoException {
        int boardID = saveBoard(boards.get(boardChoice));
        Game game = new Game(new User[numOfPlayers], boardDao.getBoard(boardID), false);
        return gameDao.createGame(game);
    }

    @Override
    public int saveBoard(Board board) throws ServiceException, DaoException {
        int savedBoardId = boardDao.createBoard(board);
        if (savedBoardId < 0) {
            throw new ServiceException("BoardDao generated invalid boardId " + savedBoardId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        spaceDao.createSpaces(savedBoardId, board.getSpaces());
        return savedBoardId;
    }

    @Override
    public Player getCurrentPlayer(int boardId) throws ServiceException, DaoException {
        if (boardId < 0) {
            throw new ServiceException("Invalid board id " + boardId, HttpStatus.BAD_REQUEST);
        }
        Board board = boardDao.getBoard(boardId);
        if (board == null) {
            throw new ServiceException("No board found for board id " + boardId, HttpStatus.NOT_FOUND);
        }
        Player player = board.getCurrentPlayer();
        if (player == null) {
            throw new ServiceException("The board with id " + boardId + " has no current player", HttpStatus.NOT_FOUND);
        }
        return player;
    }

    @Override
    public void setCurrentPlayer(int boardId, int playerId) throws ServiceException, DaoException {
        if (boardId < 0) {
            throw new ServiceException("Invalid board id " + boardId, HttpStatus.BAD_REQUEST);
        }
        if (playerId < 0) {
            throw new ServiceException("Invalid player id " + playerId, HttpStatus.BAD_REQUEST);
        }
        Board board = boardDao.getBoard(boardId);
        if (board == null) {
            throw new ServiceException("No board found for board id " + boardId, HttpStatus.NOT_FOUND);
        }
        Player player = playerDao.getPlayer(playerId);
        if (player == null) {
            throw new ServiceException("No player found for player id " + playerId, HttpStatus.NOT_FOUND);
        }
        board.setCurrentPlayer(player);
        boardDao.updateBoard(board, board.getGameId());
    }

    @Override
    public int addPlayer(int boardId, Player player) throws ServiceException, DaoException {
        if (player == null) {
            throw new ServiceException("Player to add to board was null", HttpStatus.BAD_REQUEST);
        }
        Board board = this.getBoard(boardId);
        int playerId = playerDao.addPlayer(boardId, player);
        board.addPlayer(player);
        boardDao.updateBoard(board, board.getGameId());
        return playerId;
    }

    @Override
    public void moveCurrentPlayer(int boardId, int x, int y) throws ServiceException, DaoException {
        Board board = this.getBoard(boardId);
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer == null) {
            throw new ServiceException("The board " + boardId + " has no current player", HttpStatus.BAD_REQUEST);
        }
        if (x < 0 || y < 0 || x > board.height || y > board.width) {
            throw new ServiceException("Space coordinates (" + x + "," + y + ") were invalid for board" + boardId, HttpStatus.BAD_REQUEST);
        }
        Space targetSpace = board.getSpace(x, y);
        if (targetSpace == null) {
            throw new ServiceException("Provided target space was not found", HttpStatus.NOT_FOUND);
        }
        currentPlayer.setSpace(targetSpace);
        boardDao.updateBoard(board, board.getGameId());
    }

    @Override
    public void movePlayer(Board board, int x, int y, int playerId) throws DaoException {
        Space space = board.getSpace(x, y);
        Player player = board.getPlayerById(playerId);
        if (space != null && player != null) {
            player.setSpace(space);
            boardDao.updateBoard(board, board.getGameId());
        }
    }

    @Override
    public void switchCurrentPlayer(int boardId) throws ServiceException, DaoException {
        Board board = this.getBoard(boardId);
        int amountOfPlayers = board.getPlayersNumber();
        if (amountOfPlayers <= 0) {
            throw new ServiceException("Trying to switch current player, but board has no players", HttpStatus.BAD_REQUEST);
        }
        int currentPlayerNumber = board.getPlayerNumber(board.getCurrentPlayer());
        int nextPlayerNumber = (currentPlayerNumber + 1) % amountOfPlayers;
        board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
        boardDao.updateBoard(board, board.getGameId());
    }

    @Override
    public com.example.demo.model.User createUser(String username) throws ServiceException, DaoException {
        com.example.demo.model.User createUser = new com.example.demo.model.User();
        createUser.setUserName(username);
        int userID = userDao.createUser(createUser);
        return userDao.getUser(userID);
    }

    @Override
    public com.example.demo.model.User getUser(int userID) throws ServiceException, DaoException {
        return userDao.getUser(userID);
    }

    @Override
    public com.example.demo.model.User getUser(String username) throws ServiceException, DaoException {
        return userDao.getUser(username);
    }

    @Override
    public boolean joinGame(int gameID, User user) throws ServiceException, DaoException {
        Game game = gameDao.getGame(gameID);
        return game.addUser(user);
    }


}
