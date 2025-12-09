package com.demo.todo_backend;

import com.demo.todo_backend.controller.TaskController;
import com.demo.todo_backend.dto.CreateTaskRequest;
import com.demo.todo_backend.dto.TaskResponse;
import com.demo.todo_backend.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTasks() throws Exception {
        // Prepare mock response
        TaskResponse response = new TaskResponse(
                1L,
                "Task 1",
                "Desc",
                false,
                OffsetDateTime.now(),
                null
        );

        // Mock behavior
        when(service.listRecentOpenTasks(5))
                .thenReturn(List.of(response));

        // Perform API request & validate
        mockMvc.perform(get("/api/tasks?limit=5"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].title").value("Task 1"));
    }

    @Test
    void testCreateTask() throws Exception {
        // Prepare API request body
        CreateTaskRequest req = new CreateTaskRequest("New Task", "Desc");

        // Mock service return object
        TaskResponse response = new TaskResponse(
                1L,
                "New Task",
                "Desc",
                false,
                OffsetDateTime.now(),
                null
        );

        when(service.createTask(any()))
                .thenReturn(response);

        // Perform API request & validate
        mockMvc.perform(
                       post("/api/tasks")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(objectMapper.writeValueAsString(req))
               )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.title").value("New Task"));
    }

    @Test
    void testCompleteTask() throws Exception {
        Long taskId = 1L;

        TaskResponse response = new TaskResponse(
                taskId,
                "Completed Task",
                "Some Desc",
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        when(service.markCompleted(taskId)).thenReturn(response);

        mockMvc.perform(
                       post("/api/tasks/{id}/complete", taskId)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(taskId))
               .andExpect(jsonPath("$.completed").value(true))
               .andExpect(jsonPath("$.title").value("Completed Task"));

        verify(service, times(1)).markCompleted(taskId);
    }
}
