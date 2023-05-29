package com.example.todo_app.service;

import com.example.todo_app.common.ResponseDTO;
import com.example.todo_app.domain.TodoEntity;
import com.example.todo_app.dto.TodoDTO;
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
//    public List<TodoEntity> getAllTodo() {
//        List<TodoEntity> allTodoEntity = todoRepository.findAll();
//
//        System.out.println(allTodoEntity);
//        List<TodoResponseDTO> todoList = new ArrayList<>();
//        for(TodoEntity todo : allTodoEntity) {
//            todoList.add(TodoResponseDTO.builder()
//                    .id(todo.getId())
//                    .title(todo.getTitle())
//                    .build());
//        }
//        return todoList;
//    }

    // 사용자(member)별 todo list 조회
    public List<TodoEntity> getMemTodo(final String memberId) {
        return todoRepository.findByMemberId((memberId));
    }

    // todo 생성
    public List<TodoEntity> createTodo(final TodoEntity entity) {

        //Validations
        validate(entity);
        todoRepository.save(entity);

        return todoRepository.findByMemberId(entity.getMemberId());
    }

    public List<TodoEntity> updateTodo(final TodoEntity entity) {
        // 데이터 검증
        validate(entity);

        final Optional<TodoEntity> original = todoRepository.findById(entity.getId());

        original.ifPresent(todo -> {
            todo.setTitle(entity.getTitle());
            todo.setCompletion(entity.isCompletion());
            todoRepository.save(todo);
        });

        return getMemTodo(entity.getMemberId());

    }

    public List<TodoEntity> deleteTodo(final TodoEntity entity) {
        validate(entity);

        try {
            // 2. 엔티티 삭제
            todoRepository.delete(entity);
        } catch(Exception e) {
            // 3. exception 발생 시 ID와 exception 로깅
            log.error("error deleting entity", entity.getId(), e);

            // 4. 컨트롤러로 Exception을 보냄, 데이터베이스 내부 로직을 캡슐화하려면 e를 리턴하지 않고 새 exception 오브젝트 리턴
            throw new RuntimeException("error deleting entity" + entity.getId());
        }
        // 5. 새 Todo 리스트를 가져와 리턴
        return getMemTodo(entity.getMemberId());

    }

    // 데이터 검증
    private void validate(TodoEntity entity) {
        if(entity == null) {
            log.warn("todoRequestDTO cannot be null");
            throw new RuntimeException("todoRequestDTO cannot be null");
        }

        if(entity.getMemberId() == null) {
            log.warn("Unknown member");
            throw new RuntimeException("Unknown member");
        }
    }


    public String testService() {
        return "success test";

    }

}
