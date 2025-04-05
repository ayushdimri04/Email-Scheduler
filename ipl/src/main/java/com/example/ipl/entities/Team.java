package com.example.ipl.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team extends BaseEntity {

    @Column(name = "name" , length = 25)
    private String name;

    @Column(name = "abbreviation", length = 5)
    private String abbreviation;

    @Column(name = "max_age")
    private Long maxAge;

    /*
    Team 1<---->* Player
    One Team can have many Players.
    This is the inverse side of the relation as it doesn't have F.K
     */
    @OneToMany(mappedBy = "myTeam")
    private List<Player> playerList = new ArrayList<>();

}
