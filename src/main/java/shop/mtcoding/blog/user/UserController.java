package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.core.Hello;

@RequiredArgsConstructor // final이 붙은 애들의 생성자를 생성
@Controller
public class UserController {

    private final UserService userService;
    private final HttpSession session;

    @GetMapping("/logout")  // 세션을 무효화하여 로그아웃 처리
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/login")  // 유저가 입력한 username과 password로 유저 조회해서 세션에 저장
    public String login(@Valid UserRequest.LoginDTO loginDTO, Errors errors) {
        User sessionUser = userService.로그인(loginDTO); // 세션유저를 받아야 해서 필요
        session.setAttribute("sessionUser", sessionUser);
        return "redirect:/";
    }

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO joinDTO) {
        userService.회원가입(joinDTO);
        return "redirect:/login-form";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @Hello
    @GetMapping("/login-form")
    public String loginForm() {
        System.out.println("login-form 호출됨 ===================");
        return "user/login-form";
    }

}
