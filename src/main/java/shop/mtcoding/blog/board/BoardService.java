package shop.mtcoding.blog.board;

// C -> S -> R

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception403;
import shop.mtcoding.blog.core.error.ex.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional
    public void 게시글수정(int id, BoardRequest.UpdateDTO updateDTO, User sessionUser) {
        // 1. 게시글 조회 (없으면 404)
        Board board = boardRepository.findById(id);

        // 2. 권한 체크
        if(board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정 권한이 없습니다."); // try catch
        }

        // 3. 게시글 수정
        board.setTitle(updateDTO.getTitle());
        board.setContent(updateDTO.getContent());
    }  // 트랜잭션 종료시 flush() 자동 호출 -> dirty checking

    public Board 게시글수정화면(int id, User sessionUser) {
        Board board = boardRepository.findById(id);  // return null로 하면 if null로 처리. but 여기선 404 터뜨림

        if(board.getUser().getId() != sessionUser.getId()) {
            throw new Exception403("게시글 수정 권한이 없습니다."); // try catch
        }

        return board;
    }

    public List<Board> 게시글목록보기() {  // 페이징 처리
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Transactional
    public void 게시글삭제(int id, User sessionUser) {
        // 1. Controller(클라이언트)로부터 게시글 id를 받기

        // 2. 게시글 존재 여부 확인 (못 찾으면 404)
        Board board = boardRepository.findById(id); // 10

        // 3. 내가 작성한 글인지 확인 (안 쓴거면 403 = 권한 없음)
        if (sessionUser.getId() != board.getUser().getId()) {
            throw new Exception403("권한이 없습니다");
        }

        // 4. 게시글 삭제
        boardRepository.deleteById(id);

    }

    @Transactional
    public void 게시글작성(BoardRequest.SaveDTO saveDTO, User sessionUser) {
        boardRepository.save(saveDTO.toEntity(sessionUser));
    }

    public BoardResponse.DetailDTO 상세보기(int id, User sessionUser) {
        Board board = boardRepository.findById(id); // Join (Board - User)
        return new BoardResponse.DetailDTO(board, sessionUser);
    }
}