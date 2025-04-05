package com.example.email_scheduler.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailResponse {

    private boolean success;

    private String jobId;

    private String jobGroup;

    private String message;

    public EmailResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
}
