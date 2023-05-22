package com.example.todo_app.domain;

public class Member {

    private String name; // 필수
    private String nickname; // 필수
    private String gender; // 선택
    private String email; // 선택

    public static class Builder {
        private final String name;
        private final String nickname;
        private String gender;
        private String email;

        // 필수 변수는 생성자로 값을 넣음
        public Builder(String name, String nickname) {
            this.name = name;
            this.nickname = nickname;
        }

        // 멤버 변수별 메서드: 빌더 클래스의 필드값을 set, 빌더 객체 리턴
        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        // 빌더 메서드
        public Member build() {
            Member member = new Member();
            member.name = this.name;
            member.nickname = this.nickname;
            member.gender = this.gender;
            member.email = this.email;
            return member;
        }


        Member memberEntity = new Builder("kimsky", "sky")
                .gender("여")
                .email("sky@mail.com")
                .build();







    }
}
