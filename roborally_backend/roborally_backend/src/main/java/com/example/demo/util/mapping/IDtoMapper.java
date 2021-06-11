package com.example.demo.util.mapping;

import com.example.demo.controller.GameController.BoardDto;
import com.example.demo.controller.GameController.GameDto;
import com.example.demo.controller.GameController.PlayerDto;
import com.example.demo.controller.GameController.SpaceDto;
import com.example.demo.exceptions.MappingException;
import com.example.demo.model.Game;
import com.example.demo.model.Player;
import com.example.demo.model.Space;

public interface IDtoMapper {
    PlayerDto convertToDto(Player player) throws MappingException;

    BoardDto convertToDto(com.example.demo.model.Board board) throws MappingException;

    GameDto convertToDto(Game game) throws MappingException;

    SpaceDto convertToDto(Space space) throws MappingException;

    com.example.demo.model.Board convertToEntity(BoardDto boardDto);

    Space convertToEntity(SpaceDto spaceDto, com.example.demo.model.Board board);

    Player convertToEntity(PlayerDto playerDto, com.example.demo.model.Board board) throws MappingException;

    Game convertToEntity(GameDto gameDto) throws MappingException;
}
