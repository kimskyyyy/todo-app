package com.example.todo_app.controller;

import com.example.todo_app.common.ResponseDTO;
import com.example.todo_app.domain.Todo;
import com.example.todo_app.dto.TodoRequestDTO;
import com.example.todo_app.dto.TodoResponseDTO;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;


    // todo 생성
    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequestDTO todoRequestDTO) {
        TodoResponseDTO responseDTO = todoService.createTodo(todoRequestDTO);

        return ResponseEntity.ok().body(responseDTO);
    }

    // todoList 조회(전체 조회)
    @GetMapping
    public ResponseEntity<Object> getAllTodo() {
        List<TodoResponseDTO> responseDTO = todoService.getAllTodo();
//        System.out.println("todoList 전체 조회: " + responseDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    // todo 조회
    @GetMapping("/{id}")
//    public ResponseEntity<Object> getTodo(@RequestParam(required = false) int id) {
    public ResponseEntity<Object> getTodo(@PathVariable(value = "id", required = false) String id) {
        System.out.println("id: " + id);
        List<TodoResponseDTO> responseDTO = todoService.getTodo(id);
//        ResponseDTO response = ResponseDTO.<Object>builder().data((List<Object>) responseDTO).build();
        return ResponseEntity.ok().body(responseDTO);

    }

    // 사용자(member) 별 todo 조회
    @GetMapping("/member")
    public ResponseEntity<Object> getMemTodo(@RequestParam(value ="id", required = false) String id) {
        System.out.println("id: " + id);
        List<TodoResponseDTO> responseDTO = todoService.getMemTodo(id);
        return ResponseEntity.ok().body(responseDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable("id") String id, @RequestBody TodoRequestDTO todoRequestDTO) {
        TodoResponseDTO responseDTO = todoService.updateTodo(id, todoRequestDTO);
        return ResponseEntity.ok().body(responseDTO);
//        "update complete";
    }

    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable("id") String id) {
        todoService.deleteTodo(id);
        return "delete complete";
    }


}
