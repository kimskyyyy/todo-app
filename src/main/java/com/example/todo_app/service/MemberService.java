package com.example.todo_app.service;

import com.example.todo_app.domain.MemberEntity;
import com.example.todo_app.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원 생성
    public MemberEntity create(final MemberEntity memberEntity) {
        if(memberEntity == null || memberEntity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String email = memberEntity.getEmail();
        if(memberRepository.existsByEmail(email)) {
            log.warn("Email already exists {}", email);
            throw  new RuntimeException("Email already exists");
        }

        return memberRepository.save(memberEntity);
    }

    public MemberEntity getByCredentials(final String email, final String password) {
        return memberRepository.findByEmailAndPassword(email, password);
    }
}
