package com.party.party_management.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private int status;

    public ErrorResponse(String message, String path, int status) {
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.path = path;
        this.status = status;
    }
    
    // Novo construtor simplificado
    public ErrorResponse(String message) {
        this(message, null, 0);
    }

    // Getters (sem setters para imutabilidade)
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public int getStatus() {
        return status;
    }
}