package com.example.todo_app.repository;

import com.example.todo_app.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, String> {
}
