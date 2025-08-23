package com.todolist.api.repository;

import com.todolist.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; // Importante usar Optional para manejar casos donde el usuario no existe

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA entenderá este nombre de método y generará la consulta SQL automáticamente
    Optional<User> findByEmail(String email);
}
