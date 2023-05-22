package com.example.todo_app.controller;

import com.example.todo_app.domain.Member;

public class MemberController {
    public void membertest() {
        Member member1 = new Member.Builder("wangdol", "dol")
                .gender("남")
                .email("dol@mail.com")
                .build();
        System.out.println(member1);
    }


}

