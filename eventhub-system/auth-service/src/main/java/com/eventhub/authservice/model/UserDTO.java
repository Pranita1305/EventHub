package com.eventhub.authservice.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
}