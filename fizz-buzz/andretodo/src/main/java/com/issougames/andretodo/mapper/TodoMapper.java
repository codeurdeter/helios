package com.issougames.andretodo.mapper;

import com.issougames.andretodo.dto.TodoRequest;
import com.issougames.andretodo.dto.TodoResponse;
import com.issougames.andretodo.model.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TodoMapper {
    Todo toEntity(TodoRequest request);
    TodoResponse toResponse(Todo todo);
    void updateEntity(TodoRequest request, @MappingTarget Todo todo);
}