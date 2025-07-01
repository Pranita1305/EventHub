package com.eventhub.registrationservice.service;

import com.eventhub.registrationservice.dto.EventDTO;
import com.eventhub.registrationservice.dto.UserDTO;
import com.eventhub.registrationservice.exception.ResourceNotFoundException;
import com.eventhub.registrationservice.model.Registration;
import com.eventhub.registrationservice.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    public Registration registerUser(Long userId, Long eventId) {
        // Call other microservices
        UserDTO user = externalServiceClient.getUserById(userId);
        EventDTO event = externalServiceClient.getEventById(eventId);

        if (!"ATTENDEE".equalsIgnoreCase(user.getRole())) {
            throw new ResourceNotFoundException("Only attendees can register for events");
        }

        if (registrationRepository.existsByUserIdAndEventId(userId, eventId)) {
            throw new RuntimeException("Already registered for this event");
        }

        int registeredCount = registrationRepository.findByEventId(eventId).size();

        if (registeredCount >= event.getCapacity()) {
            throw new RuntimeException("Event is already full");
        }

        Registration registration = Registration.builder()
                .userId(userId)
                .eventId(eventId)
                .registrationTime(LocalDateTime.now())
                .build();

        return registrationRepository.save(registration);
    }

    public List<Registration> getRegistrationsForUser(Long userId) {
        return registrationRepository.findByUserId(userId);
    }

    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }
}
