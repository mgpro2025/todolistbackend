package com.todolist.api.controller;

import com.todolist.api.dto.TaskDto;
import com.todolist.api.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.todolist.api.dto.CreateTaskRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.todolist.api.dto.UpdateTaskTitleRequest;

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

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskRequest request, Principal principal) {
        String userEmail = principal.getName();
        TaskDto createdTask = taskService.createTask(request, userEmail);
        return ResponseEntity.ok(createdTask);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId, Principal principal) {
        String userEmail = principal.getName();
        taskService.deleteTask(taskId, userEmail);
        // Devolvemos una respuesta 204 No Content, que es el estándar para un DELETE exitoso
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable Long taskId,
            @RequestParam boolean completed,
            Principal principal) {

        String userEmail = principal.getName();
        TaskDto updatedTask = taskService.updateTaskCompletion(taskId, completed, userEmail);
        return ResponseEntity.ok(updatedTask);
    }

    @PutMapping("/{taskId}/title")
    public ResponseEntity<TaskDto> updateTaskTitle(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskTitleRequest request,
            Principal principal) {

        String userEmail = principal.getName();
        TaskDto updatedTask = taskService.updateTaskTitle(taskId, request.getTitle(), userEmail);
        return ResponseEntity.ok(updatedTask);
    }
}
