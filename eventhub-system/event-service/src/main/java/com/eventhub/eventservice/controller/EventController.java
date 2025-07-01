package com.eventhub.eventservice.controller;

import com.eventhub.eventservice.model.Event;
import com.eventhub.eventservice.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PreAuthorize("hasAuthority('ORGANIZER') or hasAuthority('ADMIN')")
    @PostMapping("/create")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Optional<Event> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/organizer/{organizerId}")
    public List<Event> getByOrganizer(@PathVariable Long organizerId) {
        return eventService.getEventsByOrganizer(organizerId);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}
