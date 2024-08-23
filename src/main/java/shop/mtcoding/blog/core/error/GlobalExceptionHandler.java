package shop.mtcoding.blog.core.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.mtcoding.blog.core.error.ex.*;
import shop.mtcoding.blog.core.util.Script;

@RestControllerAdvice  // 모든 throw가 이쪽으로 날라옴, 무조건 데이터로 응답
public class GlobalExceptionHandler {

    // 유효성 검사 실패 (잘못된 클라이언트의 요청)
    @ExceptionHandler(Exception400.class)
    public String ex400(Exception e) {
        return Script.back(e.getMessage());
    }

    // 인증 실패 (클라이언트가 인증 없이 요청했거나, 인증 도중 실패
    @ExceptionHandler(Exception401.class)
    public String ex401(Exception e) {
        return Script.href("인증되지 않았습니다", "/login-form");
    }

    // 권한 실패 (인증은 되어있으나, 삭제하려는 게시글이 내가 적은 글이 아님)
    @ExceptionHandler(Exception403.class)
    public String ex403(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 리소스(자원)를 찾을 수 없을 때
    @ExceptionHandler(Exception404.class)
    public String ex404(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 심각한 오류 발생시 (알고 있을 때)
    @ExceptionHandler(Exception500.class)
    public String ex500(Exception e) {
        return Script.back(e.getMessage());
    }

    // 서버에서 심각한 오류 발생시 (모를 때)
    @ExceptionHandler(Exception.class)
    public String ex(Exception e) {
        return Script.back(e.getMessage());
    }
}
