package com.example.ipl.service;

import com.example.ipl.DTO.AddPlayerToTeamDTO;
import com.example.ipl.DTO.PlayerDTO;
import com.example.ipl.custom_exception.PlayerNotFoundException;
import com.example.ipl.custom_exception.TeamNotFoundException;
import com.example.ipl.entities.Player;
import com.example.ipl.repository.PlayerRepository;
import com.example.ipl.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<PlayerDTO> displayAllPlayers(Long teamId) {
        try {
            if(teamRepository.existsById(teamId)){
                List<Player> playerList = playerRepository.findAll();
                return playerList.stream().map(player->modelMapper.map(player, PlayerDTO.class)).toList();
            }else{
                throw new TeamNotFoundException("Team Not Found with Id: "+teamId);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addNewPlayer(AddPlayerToTeamDTO player) {
        try {
            if(teamRepository.existsById(player.getTeamId())){
                Player newPlayer = new Player(player.getFirstName(),player.getLastName()
                ,player.getDateOfBirth(),player.getAge(),teamRepository.findById(player.getTeamId()).orElseThrow(()->new TeamNotFoundException("Unable to Find team with id: "+player.getTeamId())));
                playerRepository.save(newPlayer);
                return "Player added successfully";
            }else{
                return "Unable to add new Player";
            }
        } catch (TeamNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String deletePlayer(Long teamId, Long playerId) {
        try {
            if(teamRepository.existsById(teamId)){
                if(playerRepository.existsById(playerId)){
                    playerRepository.deleteById(playerId);
                    return "Player deleted successfully with Id: "+playerId;
                }else{
                    throw new PlayerNotFoundException("No Player Found with Id: "+playerId);
                }
            }else{
                throw new TeamNotFoundException("No Team Found with Id: "+teamId);
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String updatePlayer(Long teamId, AddPlayerToTeamDTO player) {
        try {
            if (teamRepository.existsById(teamId)) {
                if (playerRepository.existsById(player.getId())) {
                    Player persistant = playerRepository.findById(player.getId()).orElseThrow(()->new PlayerNotFoundException("Unable to find Player with the id: "+player.getId()));
                    persistant.setFirstName(player.getFirstName());
                    persistant.setLastName(player.getLastName());
                    persistant.setAge(player.getAge());
                    persistant.setMyTeam(teamRepository.findById(player.getTeamId()).orElseThrow(()-> new TeamNotFoundException("Unable to find team with Id: "+player.getTeamId())));
                    
                    return "Player "+player.getId()+" details updated successfully";
                } else {
                    throw new PlayerNotFoundException("No Player Found with Id: " + player.getId());
                }
            } else {
                throw new TeamNotFoundException("No Team Found with Id: " + teamId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
