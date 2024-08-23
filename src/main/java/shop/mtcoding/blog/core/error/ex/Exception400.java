package shop.mtcoding.blog.core.error.ex;

// 유효성 검사 때 사용
// 대표적인 5가지 예시
public class Exception400 extends RuntimeException {  // Extends로 상속 -> Runtime을 안 터뜨림 (상태코드로 체크)

    public Exception400(String message) {
        super(message); // super(부모) = Runtime
    }
}
