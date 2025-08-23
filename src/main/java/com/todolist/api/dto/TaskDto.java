package com.todolist.api.dto;

import lombok.Data;
import java.time.Instant;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private boolean completed;
    private Instant createdAt;
    private Instant completedAt;
}
