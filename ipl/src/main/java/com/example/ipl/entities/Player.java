package com.example.ipl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player extends BaseEntity {

    @Column(name = "first_name", length = 25)
    private String firstName;

    @Column(name = "last_name", length = 25)
    private String lastName;

    @Column(name = "dob")
    private String dateOfBirth;

    private Long age;

    /*
    Player *<---->1 Team
    Many Players can have One Team.
    This is the owning side of the relation as this contains the F.K element.
     */

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team myTeam;

    public void addPlayerToTeam(Player player, Team team){
        player.setMyTeam(team);
    }

    public void deletePlayerFromTeam(Player player, Team team){
        player.setMyTeam(null);
    }
}
