package com.example.todo_app.dto;

import com.example.todo_app.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoResponseDTO {
    private String id;
    private String memberId;
    private String title;
    private String content;
    private boolean completion;

    public TodoResponseDTO(Todo todo) {
    }
}
