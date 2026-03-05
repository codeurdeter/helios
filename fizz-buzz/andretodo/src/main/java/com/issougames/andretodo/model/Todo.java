package com.issougames.andretodo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Builder.Default
    @Column(name = "open", nullable = false)
    private Boolean open = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "todo_priority", nullable = false)
    private TodoPriority todoPriority;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @ElementCollection
    @CollectionTable(name = "todo_tags", joinColumns = @JoinColumn(name = "todo_id"))
    @Column(name = "tag", nullable = false)
    private List<String> tags;
}