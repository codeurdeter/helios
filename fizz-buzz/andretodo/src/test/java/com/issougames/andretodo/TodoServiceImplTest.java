package com.issougames.andretodo;

import com.issougames.andretodo.dto.TodoRequest;
import com.issougames.andretodo.dto.TodoResponse;
import com.issougames.andretodo.exception.TodoNotFoundException;
import com.issougames.andretodo.model.TodoPriority;
import com.issougames.andretodo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TodoServiceImplTest {

    @Autowired
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoService.clear();
    }

    @Test
    void getTodos_empty_returnsEmptyList() {
        assertThat(todoService.getTodos()).isEmpty();
    }

    @Test
    void create_validRequest_returnsGeneratedId()  {
        TodoRequest todo = TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build();

        TodoResponse response = todoService.create(todo);

        assertThat(response.getId())
                .isNotNull()
                .isPositive();
    }


    @Test
    void getTodos_notEmptyDB_returnsOneTodo(){
        TodoRequest todo = TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build();

        todoService.create(todo);

        assertThat(todoService.getTodos())
                .isNotEmpty()
                .hasSize(1);
    }

    @Test
    void getTodoById_existingId_returnsTodo() {
        TodoResponse created = todoService.create(TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build());

        TodoResponse found = todoService.getTodoById(created.getId());

        assertThat(found.getId()).isEqualTo(created.getId());
        assertThat(found.getTitle()).isEqualTo("Buy milk");
    }

    @Test
    void getTodoById_nonExistingId_throwsException() {
        assertThatThrownBy(() -> todoService.getTodoById(999L))
                .isInstanceOf(TodoNotFoundException.class);
    }

    @Test
    void deleteTodo_existingId_removesFromList() {
        TodoResponse created = todoService.create(TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build());

        todoService.deleteTodo(created.getId());

        assertThat(todoService.getTodos()).isEmpty();
    }

    @Test
    void deleteTodo_nonExistingId_throwsException() {
        assertThatThrownBy(() -> todoService.deleteTodo(999L))
                .isInstanceOf(TodoNotFoundException.class);
    }

    @Test
    void setTodoOpen_existingId_updatesOpen() {
        TodoResponse created = todoService.create(TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build());

        todoService.setTodoOpen(created.getId(), false);

        assertThat(todoService.getTodoById(created.getId()).isOpen()).isFalse();
    }

    @Test
    void setTodoOpen_nonExistingId_throwsException() {
        assertThatThrownBy(() -> todoService.setTodoOpen(999L, false))
                .isInstanceOf(TodoNotFoundException.class);
    }

    @Test
    void modifyTodo_existingId_updatesFields() {
        TodoResponse created = todoService.create(TodoRequest.builder()
                .title("Buy milk")
                .todoPriority(TodoPriority.LOW)
                .build());

        todoService.modifyTodo(created.getId(), TodoRequest.builder()
                .title("Buy eggs")
                .todoPriority(TodoPriority.HIGH)
                .build());

        TodoResponse updated = todoService.getTodoById(created.getId());
        assertThat(updated.getTitle()).isEqualTo("Buy eggs");
        assertThat(updated.getTodoPriority()).isEqualTo(TodoPriority.HIGH);
    }

    @Test
    void modifyTodo_nonExistingId_throwsException() {
        assertThatThrownBy(() -> todoService.modifyTodo(999L, TodoRequest.builder()
                .title("Buy eggs")
                .todoPriority(TodoPriority.HIGH)
                .build()))
                .isInstanceOf(TodoNotFoundException.class);
    }
}
