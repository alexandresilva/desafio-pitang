package com.party.party_management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequest {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(min = 3, max = 100)
    private String fullName;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    // Campos opcionais para atualização
    @Size(min = 6, max = 20, message = "Senha deve ter entre 6 e 20 caracteres")
    private String password;
}