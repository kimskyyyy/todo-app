package com.example.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoRequestDTO {
    private String id;
    private String memberId;
    private String title;
    private String content;
    private boolean completion;
}
