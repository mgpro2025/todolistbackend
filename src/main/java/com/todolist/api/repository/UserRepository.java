package com.todolist.api.repository; // El paquete es .repository

import com.todolist.api.entity.User; // Importamos la clase User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Por ahora, vacío.
}
