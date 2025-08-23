package com.todolist.api.repository; // El paquete es .repository

import com.todolist.api.entity.Task; // Importamos la clase Task
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Por ahora, vacío.
}
