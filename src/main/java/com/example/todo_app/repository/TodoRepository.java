package com.example.todo_app.repository;

import com.example.todo_app.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
    Optional<Todo> findByMemberId(String memberId);
}
