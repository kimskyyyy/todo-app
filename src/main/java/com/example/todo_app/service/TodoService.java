package com.example.todo_app.service;

import com.example.todo_app.domain.Todo;
import com.example.todo_app.dto.TodoRequestDTO;
import com.example.todo_app.dto.TodoResponseDTO;
import com.example.todo_app.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;

    // todo 전체 리스트 조회
    public List<TodoResponseDTO> getAllTodo() {
        List<Todo> allTodo = todoRepository.findAll();

        System.out.println(allTodo);
        List<TodoResponseDTO> todoList = new ArrayList<>();
        for(Todo todo : allTodo) {
            todoList.add(TodoResponseDTO.builder()
                    .id(todo.getId())
                    .memberId(todo.getMemberId())
                    .title(todo.getTitle())
                    .content(todo.getContent())
                    .build());

        }

        return todoList;

    }

    // todo 단건 조회
    public List<TodoResponseDTO> getTodo(String id) {
        Optional<Todo> todo = todoRepository.findById(id);
        List<TodoResponseDTO> todoResponse = todo.stream().map(TodoResponseDTO::new).collect(Collectors.toList());
        return todoResponse;
    }

    // 사용자(member)별 todo list 조회
    public List<TodoResponseDTO> getMemTodo(String memberId) {
        Optional<Todo> todo = todoRepository.findByMemberId(memberId);
        List<TodoResponseDTO> memTodos = todo.stream().map(TodoResponseDTO::new).collect(Collectors.toList());
        return memTodos;
    }

    // todo 생성
    public TodoResponseDTO createTodo(TodoRequestDTO todoRequestDTO) {

        //Validations
        validate(todoRequestDTO);


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

    public TodoResponseDTO updateTodo(String id, TodoRequestDTO todoRequestDTO) {
        // 데이터 검증
        validate(todoRequestDTO);

        final Optional<Todo> original = todoRepository.findById(id);

        final Todo todo = original.get();
        todo.setMemberId((todoRequestDTO.getMemberId()));
        todo.setTitle(todoRequestDTO.getTitle());
        todo.setContent(todoRequestDTO.getContent());
        todo.setCompletion(todoRequestDTO.isCompletion());
        System.out.println(todoRequestDTO.isCompletion());
        todoRepository.save(todo);

        TodoResponseDTO todoResponseDto = TodoResponseDTO.builder()
                .memberId(todo.getMemberId())
                .title(todo.getTitle())
                .content(todo.getContent())
                .build();
        return todoResponseDto;

    }

    public void deleteTodo(String id) {

    }

    // 데이터 검증
    private void validate(TodoRequestDTO todoRequestDTO) {
        if(todoRequestDTO == null) {
            log.warn("todoRequestDTO cannot be null");
            throw new RuntimeException("todoRequestDTO cannot be null");
        }

        if(todoRequestDTO.getMemberId() == null) {
            log.warn("Unknown member");
            throw new RuntimeException("Unknown member");
        }
    }



}
