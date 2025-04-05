package com.example.ipl.service;

import com.example.ipl.DTO.AddPlayerToTeamDTO;
import com.example.ipl.DTO.PlayerDTO;
import com.example.ipl.DTO.TeamDTO;

import java.util.List;

public interface PlayerService {

    List<PlayerDTO> displayAllPlayers(Long teamId);

    String addNewPlayer(AddPlayerToTeamDTO player);

    String deletePlayer(Long teamId, Long playerId);

    String updatePlayer(Long teamId, AddPlayerToTeamDTO player);
}
