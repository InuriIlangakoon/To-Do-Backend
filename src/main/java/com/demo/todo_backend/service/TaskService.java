package com.demo.todo_backend.service;


import com.demo.todo_backend.dto.CreateTaskRequest;
import com.demo.todo_backend.dto.TaskResponse;
import com.demo.todo_backend.entity.Task;
import com.demo.todo_backend.exception.NotFoundException;
import com.demo.todo_backend.repository.TaskRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository repo;

    public TaskService(TaskRepository repo) {
        this.repo = repo;
    }

    public TaskResponse createTask( CreateTaskRequest req) {
        Task t = new Task(req.getTitle(), req.getDescription());
        Task saved = repo.save(t);
        return toDto(saved);
    }

    public List<TaskResponse> listRecentOpenTasks(int limit) {
        var page = PageRequest.of(0, Math.max(1, limit));
        List<Task> tasks = repo.findRecentOpenTasks(page);
        return tasks.stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional
    public TaskResponse markCompleted(Long id) {
        Task t = repo.findById(id).orElseThrow(() -> new NotFoundException("Task not found: " + id));
        if (!t.isCompleted()) {
            t.setCompleted(true);
            t.setCompletedAt(OffsetDateTime.now());
            repo.save(t);
        }
        return toDto(t);
    }

    private TaskResponse toDto(Task t) {
        return new TaskResponse(t.getId(), t.getTitle(), t.getDescription(), t.isCompleted(), t.getCreatedAt(), t.getCompletedAt());
    }
}