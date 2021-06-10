package com.example.demo.model;

import com.example.demo.controller.GameController.BoardDto;
import com.example.demo.controller.GameController.UserDto;

public class Game {
    private Integer gameID;
    private UserDto[] users;
    private BoardDto board;
    private boolean hasBegun;

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public UserDto[] getUsers() {
        return users;
    }

    public void setUsers(UserDto[] users) {
        this.users = users;
    }

    public BoardDto getBoard() {
        return board;
    }

    public void setBoard(BoardDto board) {
        this.board = board;
    }

    public boolean isHasBegun() {
        return hasBegun;
    }

    public void setHasBegun(boolean hasBegun) {
        this.hasBegun = hasBegun;
    }
}
