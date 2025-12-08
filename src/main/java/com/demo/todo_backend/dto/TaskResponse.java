package com.demo.todo_backend.dto;

import java.time.OffsetDateTime;

public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private OffsetDateTime createdAt;
    private OffsetDateTime completedAt;

    public TaskResponse() {}

    public TaskResponse(Long id, String title, String description, boolean completed, OffsetDateTime createdAt, OffsetDateTime completedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public Long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public boolean isCompleted() {
        return completed;
    }
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }
    public OffsetDateTime getCompletedAt() {
        return completedAt;
    }
}