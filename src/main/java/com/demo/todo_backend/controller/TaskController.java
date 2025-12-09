package com.demo.todo_backend.controller;


import com.demo.todo_backend.dto.CreateTaskRequest;
import com.demo.todo_backend.dto.TaskResponse;
import com.demo.todo_backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*")
public class TaskController {
    private final TaskService service;

    public TaskController(TaskService service) { this.service = service; }

    /**
     * Handles the creation of a new task by processing the provided task details.
     *
     * @param req the request object containing the details of the task to be created, including title and description.
     * @return a ResponseEntity containing the created task details encapsulated in a TaskResponse object,
     *         with HTTP status code 201 (Created).
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask( @Valid @RequestBody CreateTaskRequest req) {
        TaskResponse resp = service.createTask(req);
        log.info("Task created with title: {}", resp.getTitle());
        return ResponseEntity.status(201).body(resp);
    }

    /**
     * Retrieves a list of the most recent open tasks with an optional limit on the number of tasks to return.
     *
     * @param limit the maximum number of tasks to retrieve; defaults to 5 if not specified.
     * @return a ResponseEntity containing a list of TaskResponse objects representing the recent open tasks.
     */
    @GetMapping
    public ResponseEntity<List<TaskResponse>> listRecent(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(service.listRecentOpenTasks(limit));
    }

    /**
     * Marks the task with the given ID as completed.
     *
     * @param id the unique identifier of the task to be marked as completed
     * @return a ResponseEntity containing a TaskResponse object with the details of the completed task
     */
    @PostMapping("/{id}/complete")
    public ResponseEntity<TaskResponse> complete(@PathVariable Long id) {
        return ResponseEntity.ok(service.markCompleted(id));
    }
}
