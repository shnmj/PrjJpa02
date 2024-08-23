package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    @PostMapping("/board/{id}/update")
    public String update(@PathVariable("id") int id, @RequestParam("title") String title, @RequestParam("content") String content) {
        // boardRepository.updateById(title, content, id);
        return "redirect:/board/" + id;
    }


    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        User sessionUser = (User) session.getAttribute("sessionUser");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다");
        }
        boardService.게시글삭제(id, sessionUser);
        return "redirect:/board";
    }

    // subtitle=제목1&postContent=내용1
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO saveDTO) { // 스프링 기본전략 = x-www-form-urlencoded 파싱
        User sessionUser = (User) session.getAttribute("sessionUser");

        // 인증 체크 필요
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다");
        }

        boardService.게시글작성(saveDTO, sessionUser);
        return "redirect:/board";
    }


    // get, post
    @GetMapping("/board")
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
    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    // 1. 메소드 : Get
    // 2. 주소 : /board/1/update-form
    // 3. 응답 : board/update-form
    @GetMapping("/board/{id}/update-form")
    public String updateForm(@PathVariable("id") int id, HttpServletRequest request) {
        // Board board = boardRepository.findById(id);
        // request.setAttribute("model", board);
        return "board/update-form";
    }
}