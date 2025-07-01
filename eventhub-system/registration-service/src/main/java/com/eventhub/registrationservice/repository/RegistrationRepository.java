package com.eventhub.registrationservice.repository;

import com.eventhub.registrationservice.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
    boolean existsByUserIdAndEventId(Long userId, Long eventId);
    List<Registration> findByEventId(Long eventId);
}
