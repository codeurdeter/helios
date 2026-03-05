package com.issougames.andretodo.dto;

import com.issougames.andretodo.model.TodoPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoRequest {

    @NotBlank
    private String title;

    @NotNull
    private TodoPriority todoPriority;

    private LocalDateTime dueDate;
    private List<String> tags;
}