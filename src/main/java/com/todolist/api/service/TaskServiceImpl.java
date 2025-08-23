package com.todolist.api.service;

import com.todolist.api.dto.TaskDto;
import com.todolist.api.entity.User;
import com.todolist.api.repository.TaskRepository;
import com.todolist.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.todolist.api.entity.Task;
import java.time.Instant;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDto> getTasksForUser(String userEmail) {
        // 1. Buscar al usuario por su email
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Buscar todas las tareas de ese usuario y convertirlas a DTOs
        return taskRepository.findAllByUser(user) // Usaremos este método, ¡debemos crearlo!
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Método ayudante para convertir una Entidad Task a un TaskDto
    private TaskDto convertToDto(com.todolist.api.entity.Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setCompleted(task.isCompleted());
        taskDto.setCreatedAt(task.getCreatedAt());
        taskDto.setCompletedAt(task.getCompletedAt());
        return taskDto;
    }

    @Override
    public TaskDto createTask(String title, String userEmail) {
        // Buscamos al usuario para asociar la tarea
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Creamos la nueva entidad Task
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setCompleted(false);
        newTask.setCreatedAt(Instant.now()); // La fecha de creación es ahora
        newTask.setUser(user); // Asociamos la tarea al usuario

        // Guardamos la nueva tarea en la base de datos
        Task savedTask = taskRepository.save(newTask);

        // Convertimos la tarea guardada a DTO y la devolvemos
        return convertToDto(savedTask);
    }
}
