package com.party.party_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequestDTO {
    @NotBlank(message = "Username é obrigatório")
    @Size(min = 3, max = 20, message = "Username deve ter entre 3 e 20 caracteres")
    private String username;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotBlank(message = "Nome completo é obrigatório")
    private String fullName;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, max = 15, message = "Senha deve ter entre 6 e 15 caracteres")
    private String password;

    private String role;
}