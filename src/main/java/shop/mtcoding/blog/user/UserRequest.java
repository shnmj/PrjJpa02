package shop.mtcoding.blog.user;

import lombok.Data;

public class UserRequest {


    @Data
    public static class JoinDTO {  // 유저 객체로 변환하는 toEntity 메소드 제공
        private String username;
        private String password;
        private String email;

        // DTO -> UserObject
        public User toEntity() { // insert 할때만 필요
            return User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .build();
        }
    }

    @Data
    public static class LoginDTO {
        private String username;
        private String password;
    }
}
