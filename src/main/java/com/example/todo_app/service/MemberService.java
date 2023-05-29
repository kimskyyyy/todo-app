package com.example.todo_app.service;

import com.example.todo_app.domain.MemberEntity;
import com.example.todo_app.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<MemberEntity> getMembers() {
        return memberRepository.findAll();
    }

    public MemberEntity getByCredentials(final String email, final String password, final PasswordEncoder encoder) {
        final MemberEntity originalMember = memberRepository.findByEmail(email);

        // matches 메서드를 이용해 패스워드 일치 체크
        if(originalMember != null && encoder.matches(password, originalMember.getPassword())) {
            System.out.println("password: " + password + ", originalMember: " + originalMember);
            return originalMember;
        }
        return null;

    }

}
