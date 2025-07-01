package com.eventhub.registrationservice.service;

import com.eventhub.registrationservice.dto.EventDTO;
import com.eventhub.registrationservice.dto.UserDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "userService", fallbackMethod = "getFallbackUser")
    public UserDTO getUserById(Long userId) {
        return restTemplate.getForObject("http://user-service/users/" + userId, UserDTO.class);
    }

    public UserDTO getFallbackUser(Long userId, Throwable throwable) {
        return new UserDTO(userId, "Unavailable", "user@fallback.com", "UNKNOWN");
    }

    @CircuitBreaker(name = "eventService", fallbackMethod = "getFallbackEvent")
    public EventDTO getEventById(Long eventId) {
        return restTemplate.getForObject("http://event-service/events/" + eventId, EventDTO.class);
    }

    public EventDTO getFallbackEvent(Long eventId, Throwable throwable) {
        return new EventDTO(eventId, "Fallback Event", "N/A", "N/A", null, null, -1L, 0);
    }
}
