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

    @Override
    public void deleteTask(Long taskId, String userEmail) {
        // 1. Buscamos la tarea por su ID
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // 2. Comprobación de seguridad: verificamos que la tarea pertenece al usuario autenticado
        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Acceso denegado: no tienes permiso para eliminar esta tarea.");
        }

        // 3. Si todo está en orden, eliminamos la tarea
        taskRepository.delete(task);
    }

    @Override
    public TaskDto updateTaskCompletion(Long taskId, boolean completed, String userEmail) {
        // 1. Buscamos la tarea
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        // 2. Verificamos que la tarea pertenece al usuario
        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Acceso denegado");
        }

        // 3. Actualizamos el estado y la fecha de término
        task.setCompleted(completed);
        if (completed) {
            task.setCompletedAt(Instant.now()); // Si se completa, se guarda la fecha
        } else {
            task.setCompletedAt(null); // Si se desmarca, se borra la fecha
        }

        // 4. Guardamos los cambios en la base de datos
        Task updatedTask = taskRepository.save(task);

        // 5. Devolvemos la tarea actualizada
        return convertToDto(updatedTask);
    }

    @Override
    public TaskDto updateTaskTitle(Long taskId, String newTitle, String userEmail) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (!task.getUser().getEmail().equals(userEmail)) {
            throw new RuntimeException("Acceso denegado");
        }

        task.setTitle(newTitle);
        Task updatedTask = taskRepository.save(task);

        return convertToDto(updatedTask);
    }
}
