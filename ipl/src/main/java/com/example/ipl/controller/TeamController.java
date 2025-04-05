package com.example.ipl.controller;

import com.example.ipl.DTO.ApiResponseDTO;
import com.example.ipl.DTO.TeamDTO;
import com.example.ipl.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ipl/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/get-all-teams")
    public ResponseEntity<?> displayAllTeam(){
       System.out.println("In displayAllTeam: "+getClass());
       return ResponseEntity.ok(teamService.displayAllTeam());
    }

    @PostMapping("/add-new-team")
    public ResponseEntity<?> addNewTeam(@RequestBody TeamDTO team){
        System.out.println("In addNewTeam: "+getClass());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDTO(teamService.addNewTeam(team)));
    }

    @DeleteMapping("/delete-team/{teamId}")
    public ResponseEntity<?> deleteTeam(@PathVariable Long teamId){
        System.out.println("In deleteTeam: "+getClass());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(teamService.deleteTeam(teamId)));
    }

    @PutMapping("/update-team/{teamId}")
    public ResponseEntity<?> updateTeam(@PathVariable Long teamId,@RequestBody TeamDTO team){
        System.out.println("In updateTeam: "+getClass());
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponseDTO(teamService.updateTeam(teamId,team)));
    }
}
