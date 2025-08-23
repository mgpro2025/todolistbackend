package com.todolist.api.controller;

import com.todolist.api.dto.TaskDto;
import com.todolist.api.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal; // Importar Principal
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getUserTasks(Principal principal) {
        // 'Principal' es un objeto de Spring Security que contiene
        // la información del usuario autenticado. Su método getName()
        // nos devuelve el username, que en nuestro caso es el email.
        String userEmail = principal.getName();
        List<TaskDto> tasks = taskService.getTasksForUser(userEmail);
        return ResponseEntity.ok(tasks);
    }
}
