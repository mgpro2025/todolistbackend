package com.todolist.api.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateTaskRequest {
    private String title;
    private LocalDate dueDate;
}