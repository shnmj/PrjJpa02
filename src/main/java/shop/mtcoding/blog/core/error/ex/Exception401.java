package shop.mtcoding.blog.core.error.ex;

// 인증관리 (인증이 안됐을 때 터뜨림)
public class Exception401 extends RuntimeException {

    public Exception401(String message) {
        super(message);
    }
}
