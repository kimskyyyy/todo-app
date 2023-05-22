package com.example.todo_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Builder
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
}
