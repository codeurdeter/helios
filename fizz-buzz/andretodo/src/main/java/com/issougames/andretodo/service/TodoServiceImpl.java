package com.issougames.andretodo.service;

import com.issougames.andretodo.dto.TodoRequest;
import com.issougames.andretodo.dto.TodoResponse;
import com.issougames.andretodo.exception.TodoNotFoundException;
import com.issougames.andretodo.mapper.TodoMapper;
import com.issougames.andretodo.model.Todo;
import com.issougames.andretodo.repository.TodoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;
    private final TodoMapper mapper;

    @Override
    public List<TodoResponse> getTodos() {
        return repo.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<TodoResponse> getTodos(Pageable pageable) {
        return repo.findAll(pageable).getContent().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public TodoResponse getTodoById(Long id) {
        return repo.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    @Override
    @Transactional
    public TodoResponse create(TodoRequest request) {
        return mapper.toResponse(repo.save(mapper.toEntity(request)));
    }

    @Override
    @Transactional
    public void deleteTodo(Long id) {
        if (!repo.existsById(id))
            throw new TodoNotFoundException(id);
        repo.deleteById(id);
    }

    @Override
    @Transactional
    public void setTodoOpen(Long id, boolean open) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.setOpen(open);
    }

    @Override
    @Transactional
    public void modifyTodo(Long id, TodoRequest todoRequest) {
        Todo todo = repo.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        mapper.updateEntity(todoRequest, todo);
    }

    @Override
    @Transactional
    public void clear() {
        repo.deleteAll();
    }
}
