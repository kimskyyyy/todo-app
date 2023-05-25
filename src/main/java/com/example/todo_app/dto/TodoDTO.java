package com.example.todo_app.dto;

import com.example.todo_app.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

//@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoRequestDTO {
    private String id;
    private String memberId;
    private String title;
    private String content;

    @ColumnDefault("false")
    private boolean completion;

    public TodoRequestDTO(final Todo todo) {
        this.id = todo.getId();
        this.memberId = todo.getMemberId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.completion = todo.isCompletion();
    }

    public static Todo toEntity(final TodoRequestDTO todoRequestDTO) {
        return Todo.builder()
                .id(todoRequestDTO.getId())
                .memberId(todoRequestDTO.getMemberId())
                .title(todoRequestDTO.getTitle())
                .content(todoRequestDTO.getContent())
                .completion(todoRequestDTO.isCompletion())
                .build();
    }
}
