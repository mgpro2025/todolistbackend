package com.todolist.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Le dice a Spring que esta clase contiene configuraciones.
@EnableWebSecurity // Habilita la seguridad web de Spring.
public class SecurityConfig {

    @Bean // Declara que este método crea un "bean" que Spring gestionará.
    public PasswordEncoder passwordEncoder() {
        // Usamos BCrypt, el estándar moderno para encriptar contraseñas.
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Por ahora, deshabilitamos la protección CSRF y permitimos todas las peticiones.
        // Más adelante configuraremos esto de forma segura.
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}