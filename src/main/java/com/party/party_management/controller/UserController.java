package com.party.party_management.controller;

import com.party.party_management.dto.UserCreateRequestDTO;
import com.party.party_management.dto.UserResponseDTO;
import com.party.party_management.dto.UserUpdateRequestDTO;
import com.party.party_management.exception.ErrorResponse;
import com.party.party_management.model.Role;
import com.party.party_management.model.User;
import com.party.party_management.security.SecurityUtil;
import com.party.party_management.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Controle de Usuários", description = "Operações relacionadas ao cadastro de usuários")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @NotBlank(message = "Username é obrigatório") /*novo*/
    private String username;

    @NotNull(message = "ID da role é obrigatório") /*novo*/
    private Long roleId;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    /* IMPLEMENTAÇÃO USUÁRIO */
   // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserCreateRequestDTO request) {

        // Verifica se a role existe
        Role role = userService.findRoleById(request.getRoleId());
        if (role == null) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Regra não encontrada"));
        }

        // Cria o novo usuário
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setFullName(request.getFullName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);

        // Salva o usuário
        User savedUser = userService.createUser(newUser);
        if (savedUser == null) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Falha ao criar usuário"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(convertToUserResponse(savedUser));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
    		        user.getFullName(),  // <-- adiciona aqui!
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
        if (user == null) {
            throw new IllegalArgumentException("Usuário não pode ser nulo");
        }
        
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getFullName(),
            user.getRole(),
            user.getCreatedAt()
        );
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUserName(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(convertToUserResponse(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Long currentUserId = SecurityUtil.getCurrentUserId();

        // Verifica se não é admin e está tentando deletar outro usuário
        if (!SecurityUtil.isAdmin() && !id.equals(currentUserId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }



}