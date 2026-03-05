package com.issougames.andretodo;

import com.issougames.andretodo.controller.TodoController;
import com.issougames.andretodo.dto.TodoResponse;
import com.issougames.andretodo.exception.TodoNotFoundException;
import com.issougames.andretodo.model.TodoPriority;
import com.issougames.andretodo.service.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TodoService todoService;

    @Test
    void getTodos_returnsList() throws Exception {
        when(todoService.getTodos(any())).thenReturn(List.of(
                TodoResponse.builder().id(1L).title("Buy milk").todoPriority(TodoPriority.LOW).open(true).build()
        ));

        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Buy milk"));
    }

    @Test
    void getTodoById_existingId_returnsTodo() throws Exception {
        when(todoService.getTodoById(1L)).thenReturn(
                TodoResponse.builder().id(1L).title("Buy milk").todoPriority(TodoPriority.LOW).open(true).build()
        );

        mockMvc.perform(get("/api/todos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Buy milk"));
    }

    @Test
    void getTodoById_nonExistingId_returns404() throws Exception {
        when(todoService.getTodoById(999L)).thenThrow(new TodoNotFoundException(999L));

        mockMvc.perform(get("/api/todos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createTodo_validRequest_returns201() throws Exception {
        when(todoService.create(any())).thenReturn(
                TodoResponse.builder().id(1L).title("Buy milk").todoPriority(TodoPriority.LOW).open(true).build()
        );

        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Buy milk\",\"todoPriority\":\"LOW\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void createTodo_invalidRequest_returns400() throws Exception {
        mockMvc.perform(post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\",\"todoPriority\":null}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteTodo_existingId_returns204() throws Exception {
        mockMvc.perform(delete("/api/todos/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodo_nonExistingId_returns404() throws Exception {
        doThrow(new TodoNotFoundException(999L)).when(todoService).deleteTodo(999L);

        mockMvc.perform(delete("/api/todos/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void modifyTodo_validRequest_returns204() throws Exception {
        mockMvc.perform(put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Buy eggs\",\"todoPriority\":\"HIGH\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void setTodoOpen_returns204() throws Exception {
        mockMvc.perform(patch("/api/todos/1/open").param("open", "false"))
                .andExpect(status().isNoContent());
    }
}
