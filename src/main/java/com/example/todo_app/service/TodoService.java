package com.example.todo_app.service;

import com.example.todo_app.domain.Todo;
import com.example.todo_app.dto.TodoRequestDTO;
import com.example.todo_app.dto.TodoResponseDTO;
import com.example.todo_app.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoService {
    private TodoRepository todoRepository;

    public List<TodoResponseDTO> getAllTodo() {
        List<Todo> allTodo = todoRepository.findAll();

        List<TodoResponseDTO> todoList = new ArrayList<>();
        for(Todo todo : allTodo ) {
            todoList.add(TodoResponseDTO.builder()
                    .id(todo.getId())
                    .memberId(todo.getMemberId())
                    .title(todo.getTitle())
                    .content(todo.getContent())
                    .build());

        }
        return todoList;

    }


    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {
        Todo todo = Todo.builder()
                .memberId(todoRequestDTO.getMemberId())
                .title(todoRequestDTO.getTitle())
                .content(todoRequestDTO.getContent())
                .build();
        todoRepository.save(todo);

        TodoResponseDTO todoResponseDto = TodoResponseDTO.builder()
                .memberId(todo.getMemberId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .build();
    return todoResponseDto;
    }


}
