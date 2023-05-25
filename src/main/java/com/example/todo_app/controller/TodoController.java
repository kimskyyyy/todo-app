package com.example.todo_app.controller;

import com.example.todo_app.common.ResponseDTO;
import com.example.todo_app.domain.TodoEntity;
import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;


    // todo 생성
    @PostMapping
    public ResponseEntity<Object> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String temporaryMemberId = "temporary-member";

            // 1. dto -> TodoEntity, 'TodoEntity'로 변환
            TodoEntity entity = TodoDTO.toEntity(todoDTO);

            // 2. id를 NUll로 초기화
            entity.setId(null);

            // 3. 임시 사용자 아이디 설정
            entity.setMemberId(temporaryMemberId);

            // 4. 서비스를 이용해 TODO Entity 생성
            List<TodoEntity> entities = todoService.createTodo(entity);

            // 5. 스트림을 이용해 리턴된 엔터티 리스트 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 6. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 7. ResponseDTO return
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // 8.예외가 있는 경우 error 메시지 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    // todoList 조회(전체 조회)
//    @GetMapping
//    public ResponseEntity<Object> getAllTodo() {
//
//        return ResponseEntity.ok().body(responseDTO);
//    }

    // todo 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<Object> getTodo(@PathVariable(value = "id", required = false) String id) {
//        System.out.println("id: " + id);
//        List<TodoEntity> responseDTO = todoService.getTodo(id);
//        return ResponseEntity.ok().body(responseDTO);
//
//    }

    // 사용자(member) 별 todo 조회
    @GetMapping("/member/{id}")
    public ResponseEntity<Object> getMemTodo(@RequestParam(value ="id", required = false) String id) {
        // 1. 서비스 메서드의 getMemToto() 메서드를 사용해 Todo 리스트를 가져온다.
        List<TodoEntity> entities = todoService.getMemTodo(id);

        // 2. 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 3. 변환된 TOdoDTO 리스트를 이용해 ResponseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 4. ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/{id}")
//    public ResponseEntity<Object> updateTodo(@PathVariable("id") String id, @RequestBody TodoDTO todoDTO) {
    public ResponseEntity<Object> updateTodo(@RequestBody TodoDTO todoDTO) {
        String temporaryMemberId = "temporary-member";

        // 1. dto를 entity로 변환
        TodoEntity entity = TodoDTO.toEntity(todoDTO);

        // 2. id를 temporaryMemberId로 초기화
        entity.setMemberId(temporaryMemberId);

        // 3. 서비스를 이용해 entity 업데이트
        List<TodoEntity> entities = todoService.updateTodo(entity);

        // 4. 스트림을 이용해 리턴된 엔티티 리스트를
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // 6. ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String temporaryMemberId = "temporary-member";

            // 1. TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(todoDTO);

            // 2. 임시 사용자 아이디 설정
            entity.setMemberId(temporaryMemberId);

            // 3. 서비스를 이용해 ENTITY 삭제
            List<TodoEntity> entities = todoService.deleteTodo(entity);

            // 4. 자바 스트림을 이용해 리턴된 엔터티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // 5. 변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 6. ResponseDTO 리턴
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
           // 7. 예외가 있는 경우 dto 대신 error에 메시지를 넣어 리턴
           String error = e.getMessage();
           ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
           return ResponseEntity.badRequest().body(response);
        }

    }


}
