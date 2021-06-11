package com.example.demo.model;

import com.example.demo.controller.GameController.UserDto;

public class Game {
    private Integer gameID;
    private User[] users;
    private Board board;
    private boolean hasBegun;

    public Game() {

    }

    public Game(User[] users, Board board, boolean hasBegun) {
        this.users = users;
        this.board = board;
        this.hasBegun = hasBegun;

    }

    public Integer getGameID() {
        return gameID;
    }

    public void setGameID(Integer gameID) {
        this.gameID = gameID;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public boolean addUser(com.example.demo.model.User user) {
        for (int i = 0; i < users.length; i++) {
            if (users[i] == null) {
                users[i] = user;
                return true;
            }
        }
        return false;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isHasBegun() {
        return hasBegun;
    }

    public void setHasBegun(boolean hasBegun) {
        this.hasBegun = hasBegun;
    }
}
