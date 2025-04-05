package com.example.ipl.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ApiResponseDTO {

    private LocalDateTime timeStamp;

    private String message;

    public ApiResponseDTO(String message){
        this.message = message;
        timeStamp = LocalDateTime.now();
    }
}
