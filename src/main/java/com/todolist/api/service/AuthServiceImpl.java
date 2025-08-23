package com.todolist.api.service;

import com.todolist.api.entity.User;
import com.todolist.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Le dice a Spring que esta clase es un componente de servicio.
public class AuthServiceImpl implements AuthService {

    // Inyectamos las dependencias que necesitamos.
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Spring usará la inyección de dependencias a través del constructor.
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String email, String password) {
        // Aquí va la lógica de negocio.
        // Por ahora, solo creamos y guardamos el usuario.
        // Más adelante añadiremos la validación de email duplicado.

        User newUser = new User();
        newUser.setEmail(email);
        // ¡Importante! Encriptamos la contraseña antes de guardarla.
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }
}
