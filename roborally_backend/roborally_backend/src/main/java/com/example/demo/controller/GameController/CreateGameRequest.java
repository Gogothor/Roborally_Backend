package com.example.demo.controller.GameController;

public class CreateGameRequest{
    private int numOfPlayers;
    private String boardChoice;

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public String getBoardChoice() {
        return boardChoice;
    }

    public void setBoardChoice(String boardChoice) {
        this.boardChoice = boardChoice;
    }
}