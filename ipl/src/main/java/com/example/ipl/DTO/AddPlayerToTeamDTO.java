package com.example.ipl.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddPlayerToTeamDTO extends BaseDTO{

    private String firstName;

    private String lastName;

    private String dateOfBirth;

    private Long age;

    private Long teamId;
}
