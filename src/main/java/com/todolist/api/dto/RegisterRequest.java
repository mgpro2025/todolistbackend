package com.todolist.api.dto;

import lombok.Data;

@Data // Lombok genera getters y setters automáticamente
public class RegisterRequest {
    private String email;
    private String password;
}
