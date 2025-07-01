package com.eventhub.authservice.model;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class AuthRequest {

    private String email;
    private String password;
}