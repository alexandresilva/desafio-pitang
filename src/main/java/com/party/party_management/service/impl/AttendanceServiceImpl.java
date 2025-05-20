package com.party.party_management.service.impl;

import com.party.party_management.component.UserMapper;
import com.party.party_management.dto.UserResponse;
import com.party.party_management.exception.ConflictException;
import com.party.party_management.exception.ResourceNotFoundException;
import com.party.party_management.model.Attendance;
import com.party.party_management.model.Event;
import com.party.party_management.model.User;
import com.party.party_management.repository.AttendanceRepository;
import com.party.party_management.repository.EventRepository;
import com.party.party_management.repository.UserRepository;
import com.party.party_management.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void registerAttendance(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (attendanceRepository.existsByEventAndUser(event, user)) {
            throw new ConflictException("Usuário já está inscrito neste evento");
        }

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setUser(user);
        attendance.setRegistrationDate(LocalDateTime.now());

        attendanceRepository.save(attendance);
    }

    @Override
    public void cancelAttendance(Long eventId, Long userId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Attendance attendance = attendanceRepository.findByEventAndUser(event, user)
                .orElseThrow(() -> new ResourceNotFoundException("Inscrição não encontrada"));

        attendanceRepository.delete(attendance);
    }

    @Override
    public List<UserResponse> getEventAttendees(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        return attendanceRepository.findByEvent(event).stream()
                .map(Attendance::getUser)
                .map(userMapper::toDto)
                .toList();
    }
}