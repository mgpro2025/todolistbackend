package com.todolist.api.repository;

import com.todolist.api.entity.Task;
import com.todolist.api.entity.User; // Importar User
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // Importar List

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Spring Data JPA creará la consulta para buscar todas las tareas
    // que pertenecen a un objeto User específico.
    List<Task> findAllByUser(User user);
}
