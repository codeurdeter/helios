package com.issougames.andretodo.service;

import com.issougames.andretodo.dto.TodoRequest;
import com.issougames.andretodo.dto.TodoResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TodoService {

    List<TodoResponse> getTodos();
    List<TodoResponse> getTodos(Pageable pageable);

    TodoResponse create(TodoRequest request);
    TodoResponse getTodoById(Long id);
    void deleteTodo(Long id);
    void setTodoOpen(Long id, boolean open);
    void modifyTodo(Long id, TodoRequest todoRequest);
    void clear();
}
