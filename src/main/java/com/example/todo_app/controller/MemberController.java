package com.example.todo_app.controller;

import com.example.todo_app.common.ResponseDTO;
import com.example.todo_app.domain.MemberEntity;
import com.example.todo_app.dto.MemberDTO;
import com.example.todo_app.security.TokenProvider;
import com.example.todo_app.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/auth")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<Object> registerMember(@RequestBody MemberDTO memberDTO) {
        try {
            // 리퀘스트를 이옹해 저장할 회원 생성
            MemberEntity memberEntity = MemberEntity.builder()
                    .email(memberDTO.getEmail())
                    .username(memberDTO.getUsername())
                    .password(passwordEncoder.encode(memberDTO.getPassword()))
                    .build();

            // 서비스를 이용해 리포지터리에 회원 저장
            MemberEntity registeredMember = memberService.create(memberEntity);
            MemberDTO responseMemberDTO = MemberDTO.builder()
                    .email(registeredMember.getEmail())
                    .id(registeredMember.getId())
                    .username(registeredMember.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseMemberDTO);
        } catch (Exception e){
            // 회원 정보는 항상 하나이므로 리스트로 만들어야하는 ResponseDTO를 사용하지 않고 MemberDTO 리턴
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }

    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@RequestBody MemberDTO memberDTO) {
        MemberEntity memberEntity = memberService.getByCredentials(
                memberDTO.getEmail(),
                memberDTO.getPassword(),
                passwordEncoder
        );
        System.out.println("로그인한 회원 정보: " + memberEntity);

        if(memberEntity != null) {
            // 토큰 생성
            final String token = tokenProvider.create(memberEntity);
            final MemberDTO responseMemberDTO = MemberDTO.builder()
                    .email(memberEntity.getEmail())
                    .id(memberEntity.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseMemberDTO);
        } else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed")
                    .build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);

        }
    }

    @GetMapping
    public ResponseEntity<Object> getMembers() {
        List<MemberEntity> member = memberService.getMembers();

        final String token = tokenProvider.create(member.get(0));
        final MemberDTO responseMemberDTO = MemberDTO.builder()
                .email(member.get(0).getEmail())
                .id(member.get(0).getId())
                .token(token)
                .build();
        return ResponseEntity.ok().body(responseMemberDTO);
    }





}

