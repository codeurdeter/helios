package com.issougames.andretodo.controller;


import com.issougames.andretodo.dto.TodoRequest;
import com.issougames.andretodo.dto.TodoResponse;
import com.issougames.andretodo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TodoResponse createTodo(@Valid @RequestBody TodoRequest request){
        return todoService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodo(id);
    }

    @GetMapping
    public List<TodoResponse> getTodos(Pageable pageable) {
        return todoService.getTodos(pageable);
    }

    @GetMapping("/{id}")
    public TodoResponse getTodo(@PathVariable Long id){
        return todoService.getTodoById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyTodo(@PathVariable Long id, @Valid @RequestBody TodoRequest request) {
        todoService.modifyTodo(id, request);
    }

    @PatchMapping("/{id}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setTodoOpen(@PathVariable Long id, @RequestParam boolean open) {
        todoService.setTodoOpen(id, open);
    }
}
