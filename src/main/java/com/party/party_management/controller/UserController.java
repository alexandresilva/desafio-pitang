package com.party.party_management.controller;

import com.party.party_management.dto.UserResponseDTO;
import com.party.party_management.dto.UserUpdateRequestDTO;
import com.party.party_management.model.User;
import com.party.party_management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        List<UserResponseDTO> response = users.stream()
                .map(user -> convertToDto(user))
                .toList();
        return ResponseEntity.ok(response);
    }

    private UserResponseDTO convertToDto(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(convertToUserResponse(user));
    }

    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateRequestDTO request) {

        User user = userService.findById(id);

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        User updatedUser = userService.updateUser(user);

        return ResponseEntity.ok(convertToUserResponse(updatedUser));
    }

    private UserResponseDTO convertToUserResponse(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}