package com.example.todo_app.dto;

import com.example.todo_app.domain.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
//    private String memberId;

    @ColumnDefault("false")
    private boolean completion;

    public TodoDTO(final TodoEntity todoEntity) {
        this.id = todoEntity.getId();
//        this.memberId = todoEntity.getMemberId();
        this.title = todoEntity.getTitle();
        this.completion = todoEntity.isCompletion();
    }

    public static TodoEntity toEntity(final TodoDTO todoDTO) {
        return TodoEntity.builder()
                .id(todoDTO.getId())
//                .memberId(todoDTO.getMemberId())
                .title(todoDTO.getTitle())
                .completion(todoDTO.isCompletion())
                .build();
    }
}
