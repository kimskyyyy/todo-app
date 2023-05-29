package com.example.todo_app.repository;

import com.example.todo_app.domain.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, String> {

    MemberEntity findByEmail(String email);
    Boolean existsByEmail(String email);
    MemberEntity findByEmailAndPassword(String email, String password);

    MemberEntity findByUsername(String username);
}
