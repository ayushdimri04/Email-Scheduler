package com.example.ipl.service;

import com.example.ipl.DTO.TeamDTO;
import com.example.ipl.custom_exception.TeamNotFoundException;
import com.example.ipl.entities.Team;
import com.example.ipl.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TeamServiceImpl implements TeamService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TeamDTO> displayAllTeam() {
        System.out.println("In displayAllTeam: "+getClass());
        try {
            List<Team> teamList = teamRepository.findAll();
            return teamList.stream().map(team->modelMapper.map(team, TeamDTO.class)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addNewTeam(TeamDTO team) {
        System.out.println("In addNewTeam: "+getClass());
        try {
            teamRepository.save(modelMapper.map(team,Team.class));
            return "Team Added Successfully!!";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deleteTeam(Long teamId) {
        System.out.println("In deleteTeam: "+getClass());
        try {
            if(teamRepository.existsById(teamId)){
                teamRepository.deleteById(teamId);
                return "Team with id "+teamId+" is deleted successfully!";
            }
            return "Unable to delete team with id "+teamId;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updateTeam(Long teamId, TeamDTO team) {
        System.out.println("In updateTeam: "+getClass());
        try {
            if(teamRepository.existsById(teamId)){
                Team persistantTeam = teamRepository.findById(teamId).orElseThrow(()->new TeamNotFoundException("Unable to find team with id "+teamId));
                persistantTeam.setName(team.getName());
                persistantTeam.setAbbreviation(team.getAbbreviation());
                persistantTeam.setMaxAge(team.getMaxAge());
                return "Team with id "+teamId+" is updated successfully!";
            }
        } catch (TeamNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "Unable to update team with id "+teamId;
    }
}
