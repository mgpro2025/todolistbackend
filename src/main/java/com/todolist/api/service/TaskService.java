package com.todolist.api.service;

import com.todolist.api.dto.TaskDto;
import java.util.List;

public interface TaskService {
    List<TaskDto> getTasksForUser(String userEmail);
    TaskDto createTask(String title, String userEmail);
}
