package com.eventhub.registrationservice.controller;

import com.eventhub.registrationservice.model.Registration;
import com.eventhub.registrationservice.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PreAuthorize("hasAuthority('ATTENDEE')")
    @PostMapping
    public Registration register(@RequestParam Long userId, @RequestParam Long eventId) {
        return registrationService.registerUser(userId, eventId);
    }

    @GetMapping("/user/{userId}")
    public List<Registration> getUserRegistrations(@PathVariable Long userId) {
        return registrationService.getRegistrationsForUser(userId);
    }

    @GetMapping
    public List<Registration> getAll() {
        return registrationService.getAllRegistrations();
    }
}
