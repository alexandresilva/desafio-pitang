package com.party.party_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) // Isso define o código HTTP 409 para a exceção
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}