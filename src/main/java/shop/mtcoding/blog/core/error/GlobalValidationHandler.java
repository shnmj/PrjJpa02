package shop.mtcoding.blog.core.error;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import shop.mtcoding.blog.core.error.ex.Exception400;

@Component
@Aspect  // AOP 등록
public class GlobalValidationHandler {

    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping) ")
    public void validCheck(JoinPoint jp) {
        Object[] args = jp.getArgs(); // 메소드 매개변수
        for(Object arg : args) {
            if(arg instanceof Errors){ // instanceof = 타입 검사
                Errors errors = (Errors) arg;

                if(errors.hasErrors()) { // 에러가 있으면 true 뜸
                    throw new Exception400("유효성 검사 실패입니다."); // title이나 content 중 하나만 작성후 완료버튼 클릭시 발동
                }
            }
        }

    }

    @Around("@annotation(shop.mtcoding.blog.core.Hello)")
    //@Before("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public Object hello(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("aop hello1 before 호출됨");
        Object proceed = jp.proceed(); // jp.proceed = @Hello 어노테이션이 붙은 함수 호출
        System.out.println("aop hello1 after 호출됨");
        return proceed;

    }
}
