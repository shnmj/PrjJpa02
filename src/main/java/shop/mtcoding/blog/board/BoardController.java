package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog.core.error.ex.Exception401;
import shop.mtcoding.blog.user.User;

import java.util.List;

// 식별자 요청 받기, 응답 하기
@RequiredArgsConstructor
@Controller // 식별자 요청을 받을 수 있다
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;


    // url : http://localhost:8080/board/1/update
    // body : title=제목1변경&content=내용1변경
    // content-type : x-www-form-urlencoded
    @PostMapping("/api/board/{id}/update")
    public String update(@PathVariable("id") int id, BoardRequest.UpdateDTO updateDTO) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글수정(id, updateDTO, sessionUser);
        return "redirect:/board/" + id;
    } // 부가 로직


    @PostMapping("/api/board/{id}/delete")
    public String delete(@PathVariable("id") int id, @Valid BoardRequest.UpdateDTO updateDTO, Errors errors) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardService.게시글삭제(id, sessionUser);
        return "redirect:/";
    }

    // subtitle=제목1&postContent=내용1
    @PostMapping("/api/board/save")
    public String save(@Valid BoardRequest.SaveDTO saveDTO, Errors errors) { // 스프링 기본전략 = x-www-form-urlencoded 파싱
        User sessionUser = (User) session.getAttribute("sessionUser");
        // 문법 발동방법 : DTO 앞에 @Valid 붙이면 DTO가 생성될 때 앞에 anotation 분석해서 null이나 공백이면 Errors로 객체를 다 넘김

        // 인증 체크 필요
        boardService.게시글작성(saveDTO, sessionUser);
        return "redirect:/";
    }


    // get, post
    @GetMapping("/") // mainpage
    public String list(HttpServletRequest request) {
        List<Board> boardList = boardService.게시글목록보기();
        request.setAttribute("models", boardList);

        return "board/list";
    }

    // 1. 메소드 : Get
    // 2. 주소 : /board/1
    // 3. 응답 : board/detail
    @GetMapping("/board/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
//        Board board = boardRepository.findById(id);
//        request.setAttribute("model", board);
//        request.setAttribute("isOwner", false);

        User sessionUser = (User) session.getAttribute("sessionUser");

        BoardResponse.DetailDTO detailDTO = boardService.상세보기(id, sessionUser);
        request.setAttribute("model", detailDTO);

        return "board/detail";
    }


    // 1. 메소드 : Get
    // 2. 주소 : /board/save-form
    // 3. 응답 : board/save-form
    @GetMapping("/api/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 1. 메소드 : Get
    // 2. 주소 : /board/1/update-form
    // 3. 응답 : board/update-form
    @GetMapping("/api/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");

        Board board = boardService.게시글수정화면(id, sessionUser);
        request.setAttribute("model", board);

        return "board/update-form";
    }
}