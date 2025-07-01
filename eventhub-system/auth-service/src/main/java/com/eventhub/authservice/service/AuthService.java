package com.eventhub.authservice.service;

import com.eventhub.authservice.exception.InvalidCredentialsException;
import com.eventhub.authservice.model.AuthRequest;
import com.eventhub.authservice.model.AuthResponse;
import com.eventhub.authservice.model.UserDTO;
import com.eventhub.authservice.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse login(AuthRequest authRequest){
        String url = "http://user-service/users/email/" + authRequest.getEmail();
        UserDTO user = restTemplate.getForObject(url, UserDTO.class);

        if(user == null || user.getPassword().equals(authRequest.getPaasword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token= jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole());

        return new AuthResponse(toke,user.getRole(),user,getId());
    }
}