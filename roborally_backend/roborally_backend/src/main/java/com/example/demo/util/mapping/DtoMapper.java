package com.example.demo.util.mapping;

import com.example.demo.controller.GameController.*;
import com.example.demo.exceptions.MappingException;
import com.example.demo.model.*;
import org.springframework.stereotype.Service;

@Service
public class DtoMapper implements IDtoMapper {
    public PlayerDto convertToDto(Player player) throws MappingException {
        if (player == null) {
            throw new MappingException("Player was null");
        }
        PlayerDto playerDto = new PlayerDto();
        playerDto.setBoardId(player.board.getGameId());
        playerDto.setPlayerId(player.getPlayerId());
        playerDto.setPlayerName(player.getName());
        playerDto.setPlayerColor(player.getColor());

        if (player.getSpace() != null) {
            playerDto.setX(player.getSpace().x);
            playerDto.setY(player.getSpace().y);
        }

        return playerDto;
    }


    public BoardDto convertToDto(Board board) throws MappingException {
        if (board == null) {
            throw new MappingException("BoardDto was null");
        }
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getGameId());
        boardDto.setBoardName(board.boardName);
        boardDto.setHeight(board.height);
        boardDto.setWidth(board.width);

        if (board.getCurrentPlayer() != null) {
            boardDto.setCurrentPlayerDto(convertToDto(board.getCurrentPlayer()));
        }

        boardDto.setPlayerDtos(new PlayerDto[board.getPlayersNumber()]);
        if (board.getPlayersNumber() > 0) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                boardDto.getPlayerDtos()[i] = convertToDto(board.getPlayer(i));
            }
        }

        if (board.getSpaces().length > 0 && board.getSpaces()[0].length > 0) {
            boardDto.setSpaceDtos(new SpaceDto[board.getSpaces().length][board.getSpaces()[0].length]);
            for (int i = 0; i < board.getSpaces().length; i++) {
                for (int j = 0; j < board.getSpaces()[i].length; j++) {
                    boardDto.getSpaceDtos()[i][j] = convertToDto(board.getSpace(i, j));
                }
            }
        }

        return boardDto;
    }

    public GameDto convertToDto(Game game) throws MappingException {
        if (game == null) {
            throw new MappingException("Game was null");
        }
        GameDto gameDto = new GameDto();
        gameDto.setGameID(game.getGameID());
        User[] users = game.getUsers();
        UserDto[] userDto = new UserDto[users.length];
        for (int i = 0; i < userDto.length; i++) {
            if (users[i] != null) {
                userDto[i] = convertToDto(users[i]);
            }
        }

        gameDto.setUsers(userDto);
        gameDto.setBoard(convertToDto(game.getBoard()));
        gameDto.setHasBegun(game.isHasBegun());


        return gameDto;
    }

    public SpaceDto convertToDto(Space space) throws MappingException {
        if (space == null) {
            throw new MappingException("Space was null");
        }
        SpaceDto spaceDto = new SpaceDto();
        spaceDto.setX(space.x);
        spaceDto.setY(space.y);
        if (space.getPlayer() != null) {
            spaceDto.setPlayerId(space.getPlayer().getPlayerId());
        }
        return spaceDto;
    }

    @Override
    public UserDto convertToDto(User user) throws MappingException {
        if (user == null) {
            throw new MappingException("user was null");
        }
        UserDto userDto = new UserDto();
        userDto.setUserName(user.getUserName());
        userDto.setUserID(user.getUserID());
        return userDto;
    }

    public Board convertToEntity(BoardDto boardDto) throws MappingException {
        Board board = new Board(boardDto.getWidth(), boardDto.getHeight(), boardDto.getBoardName());
        if (boardDto.getBoardId() != -1) {
            board.setGameId(boardDto.getBoardId());
        }
        for(PlayerDto playerDto : boardDto.getPlayerDtos()){
            board.addPlayer(convertToEntity(playerDto, board));
        }
        return board;
    }

    public Space convertToEntity(SpaceDto spaceDto, com.example.demo.model.Board board) {
        return new Space(board, spaceDto.getX(), spaceDto.getY());
    }

    public Player convertToEntity(PlayerDto playerDto, com.example.demo.model.Board board) throws MappingException {
        if (playerDto.getBoardId() == null) { //We cant have a player without a board id
            throw new MappingException("PlayerDto did not contain a board id");
        }
        if (board == null) { //Incase the provided board id is invalid
            throw new MappingException("BoardDto was null when trying to convert PlayerDto to Player");
        }
        if (playerDto.getPlayerId() == null) { //If we have not provided a player id, we are creating a new player
            return new Player(board, playerDto.getPlayerColor(), playerDto.getPlayerName());
        }
        return null;
    }

    public Game convertToEntity(GameDto gameDto) throws MappingException {
        Game game = new Game();
        if (gameDto.getGameID() == null) {
            throw new MappingException("gameDto does not have a gameID to get");
        }
        return null;
    }

    @Override
    public com.example.demo.model.User convertToEntity(UserDto userDto) throws MappingException {
        if (userDto == null) {
            throw new MappingException("user was null");
        }
        com.example.demo.model.User user = new com.example.demo.model.User();
        user.setUserName(userDto.getUserName());
        user.setUserID(userDto.getUserID());
        return user;
    }


}
