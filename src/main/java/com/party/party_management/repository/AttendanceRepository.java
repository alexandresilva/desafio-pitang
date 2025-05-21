package com.party.party_management.repository;

import com.party.party_management.model.Attendance;
import com.party.party_management.model.Event;
import com.party.party_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    boolean existsByEventAndUser(Event event, User user);
    Optional<Attendance> findByEventAndUser(Event event, User user);
    List<Attendance> findByEvent(Event event);
}