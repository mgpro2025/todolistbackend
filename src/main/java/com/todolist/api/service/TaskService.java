package com.todolist.api.service;

import com.todolist.api.dto.TaskDto;
import java.util.List;

public interface TaskService {
    List<TaskDto> getTasksForUser(String userEmail);
    TaskDto createTask(String title, String userEmail);
    void deleteTask(Long taskId, String userEmail);
    TaskDto updateTaskCompletion(Long taskId, boolean completed, String userEmail);
    TaskDto updateTaskTitle(Long taskId, String newTitle, String userEmail);
}
