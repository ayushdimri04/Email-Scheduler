package com.example.ipl.service;

import com.example.ipl.DTO.TeamDTO;

import java.util.List;

public interface TeamService {

    List<TeamDTO> displayAllTeam();

    String addNewTeam(TeamDTO team);

    String deleteTeam(Long teamId);

    String updateTeam(Long teamId, TeamDTO team);
}
