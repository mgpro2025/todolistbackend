package com.todolist.api.dto;

import lombok.Data;
import java.time.Instant;
import java.time.LocalDate;


@Data
public class TaskDto {
    private Long id;
    private String title;
    private boolean completed;
    private Instant createdAt;
    private Instant completedAt;
    private LocalDate dueDate;
}
