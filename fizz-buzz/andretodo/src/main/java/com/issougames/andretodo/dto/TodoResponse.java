package com.issougames.andretodo.dto;

import com.issougames.andretodo.model.TodoPriority;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String title;
    private TodoPriority todoPriority;
    private LocalDateTime dueDate;
    private List<String> tags;
    private boolean open;
}