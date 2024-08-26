package shop.mtcoding.blog.board;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class UpdateDTO {  // 요청 DTO는 동일해도 중복해서 생성 ->
        private String title;
        private String content;

    }

    @Data
    public static class SaveDTO {
        @NotEmpty  // 공백,null 안됨
        private String title;
        @NotEmpty
        private String content;

        public Board toEntity(User sessionUser) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(sessionUser)
                    .build();

        }
    }


}
