package shop.mtcoding.blog.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class UserRequest {


    @Data
    public static class JoinDTO {  // 유저 객체로 변환하는 toEntity 메소드 제공
        @NotEmpty
        private String username;
        @NotEmpty
        private String password;

        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        @NotEmpty
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
