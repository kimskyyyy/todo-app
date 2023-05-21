package com.example.todo_app.controller;

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


    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequestDTO todoRequestDTO) {
        TodoResponseDTO responseDTO = todoService.createTodo(todoRequestDTO);
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getAllTodo() {
        List<TodoResponseDTO> responseDTO = todoService.getAllTodo();
        return ResponseEntity.ok().body(responseDTO);
    }

    @GetMapping("/{id}")
//    public String getTodo(@RequestParam(required = false) int id) {
    public String getTodo(@PathVariable(required = false) int id) {
        return "get id: " + id;

    }

    @PutMapping("/{id}")
    public String updateTodo() {
        return "update complete";
    }

    @DeleteMapping("/{id}")
    public String deleteTod() {
        return "delete complete";
    }


}
