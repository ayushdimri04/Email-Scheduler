package com.example.ipl.controller;

import com.example.ipl.DTO.AddPlayerToTeamDTO;
import com.example.ipl.DTO.ApiResponseDTO;
import com.example.ipl.DTO.PlayerDTO;
import com.example.ipl.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ipl/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/display-all-player/{teamId}")
    public ResponseEntity<?> displayAllPlayers(@PathVariable Long teamId){
        System.out.println("In displayAllPlayers: "+getClass());
        return ResponseEntity.ok(playerService.displayAllPlayers(teamId));
    }

    @PostMapping("/add-new-Player")
    public ResponseEntity<?> addNewPlayer(@RequestBody AddPlayerToTeamDTO player){
        System.out.println("In addNewPlayer: "+getClass());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(playerService.addNewPlayer(player)));
    }

    @DeleteMapping("/delete-player")
    public ResponseEntity<?> deletePlayer(@RequestParam Long teamId,@RequestParam Long playerId){
        System.out.println("In deletePlayer: "+getClass());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(playerService.deletePlayer(teamId,playerId)));
    }

    @PutMapping("/update-player/{teamId}")
    public ResponseEntity<?> updatePlayer(@PathVariable Long teamId,@RequestBody AddPlayerToTeamDTO player){
        System.out.println("In updatePlayer: "+getClass());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(playerService.updatePlayer(teamId,player)));
    }

}
