package com.demo.todo_backend.controller;


import com.demo.todo_backend.dto.CreateTaskRequest;
import com.demo.todo_backend.dto.TaskResponse;
import com.demo.todo_backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask( @Valid @RequestBody CreateTaskRequest req) {
        TaskResponse resp = service.createTask(req);
        return ResponseEntity.status(201).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> listRecent(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.listRecentOpenTasks(limit));
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.markCompleted(id));
    }
}
