package com.eventhub.authservice.model;

import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthResponse {

    private String token;
    private String role;
    protected Long userId;
}