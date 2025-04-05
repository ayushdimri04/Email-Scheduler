package com.example.ipl.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamDTO extends BaseDTO{

    private String name;

    private String abbreviation;

    private Long maxAge;

}
