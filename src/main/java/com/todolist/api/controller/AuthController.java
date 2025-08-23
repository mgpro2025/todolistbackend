package com.todolist.api.controller;

import com.todolist.api.dto.RegisterRequest;
import com.todolist.api.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.todolist.api.dto.LoginRequest;
import com.todolist.api.dto.LoginResponse;

@RestController // Indica que esta clase es un controlador REST (devuelve JSON).
@RequestMapping("/api/auth") // Todas las rutas en esta clase empezarán con /api/auth.
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Define un endpoint para peticiones POST a /api/auth/register.
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        // @RequestBody le dice a Spring que convierta el JSON de la petición
        // en un objeto de nuestra clase RegisterRequest.

        authService.registerUser(registerRequest.getEmail(), registerRequest.getPassword());

        // Devolvemos una respuesta HTTP 200 OK con un mensaje.
        return ResponseEntity.ok("¡Usuario registrado exitosamente!");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }
}