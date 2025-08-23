package com.todolist.api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    // ACTUALIZA EL CONSTRUCTOR para inyectar los nuevos beans
    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

    @Override
    public String loginUser(String email, String password) {
        // 1. Autenticar al usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // 2. Si la autenticación es exitosa, buscar los detalles del usuario
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // 3. Generar y devolver el token JWT
        return jwtService.generateToken(userDetails);
    }
}
