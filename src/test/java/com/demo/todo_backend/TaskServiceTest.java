package com.demo.todo_backend;

import com.demo.todo_backend.dto.TaskResponse;
import com.demo.todo_backend.exception.NotFoundException;
import com.demo.todo_backend.service.TaskService;
import com.demo.todo_backend.dto.CreateTaskRequest;
import com.demo.todo_backend.entity.Task;
import com.demo.todo_backend.repository.TaskRepository;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private final TaskRepository repo = mock(TaskRepository.class);
    private final TaskService service = new TaskService(repo);

    @Test
    void testCreateTask() {
        CreateTaskRequest req = new CreateTaskRequest("Test Task", "Description");

        Task saved = new Task();
        saved.setId(1L);
        saved.setTitle(req.getTitle());
        saved.setDescription(req.getDescription());

        when(repo.save(any(Task.class))).thenReturn(saved);

        var result = service.createTask(req);

        assertEquals("Test Task", result.getTitle());
        verify(repo, times(1)).save(any(Task.class));
    }

    @Test
    void testCompleteTask() {
        Task task = new Task();
        task.setId(1L);
        task.setCompleted(false);

        when(repo.findById(1L)).thenReturn(Optional.of(task));
        when(repo.save(task)).thenReturn(task);

        TaskResponse updated = service.markCompleted(1L);

        assertTrue(updated.isCompleted());
        verify(repo).save(task);
    }

    @Test
    void testMarkCompletedSuccessfully() {
        Long id = 1L;

        Task t = new Task();
        t.setId(id);
        t.setTitle("Task");
        t.setDescription("Desc");
        t.setCompleted(false);

        when(repo.findById(id)).thenReturn(Optional.of(t));
        when(repo.save(any(Task.class))).thenReturn(t);

        TaskResponse resp = service.markCompleted(id);

        assertTrue(resp.isCompleted());
        assertNotNull(resp.getCompletedAt());
        verify(repo, times(1)).save(t);
    }

    @Test
    void testMarkCompletedTaskNotFound() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows( NotFoundException.class, () -> service.markCompleted(99L));

        verify(repo, never()).save(any());
    }

    @Test
    void testMarkCompletedAlreadyCompletedNoSave() {
        Long id = 2L;

        Task task = new Task();
        task.setId(id);
        task.setCompleted(true);
        task.setCompletedAt( OffsetDateTime.now());

        when(repo.findById(id)).thenReturn(Optional.of(task));

        TaskResponse resp = service.markCompleted(id);

        assertTrue(resp.isCompleted());
        verify(repo, never()).save(any());
    }
}
