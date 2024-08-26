package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

//@SpringBootTest // C R e h2 -> 모든 레이어를 메모리에 다 올리고 테스트할 때 사용하는 어노테이션
@DataJpaTest // h2, em
@Import(BoardRepository.class) // br
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void updateByIdV2_test() {
        // given
        int id = 1;
        Board board = boardRepository.findById(id);

        // when
        board.setTitle("제목10");
        board.setContent("내용10");

        // 트랜잭션 종료시 flush() 발동
        em.flush(); // 현재 변경 상태를 확인해서 알아서 쿼리를 만들어서 전달하거라
        // em.persist(board); // 영속객체라 insert됨
    }

    @Test
    public void findByIdV3_test(){
        int id = 10;

        boardRepository.findByIdV3(id);

    }

    @Test
    public void findByIdV2_test() {
        int id = 1;

        Board board = boardRepository.findByIdV2(id);
        System.out.println(board.getUser().getUsername());
    }

    @Test
    public void updateById_test() {
        // given
        int id = 1;
        String title = "제목1변경";
        String content = "내용1변경";

        // when
        boardRepository.updateById(title, content, id);

        // then
        Board board = boardRepository.findById(id);
        Assertions.assertThat(board.getTitle()).isEqualTo("제목1변경");
    }


    @Test
    public void deleteById_test() {
        // given
        int id = 6;

        // when
        boardRepository.deleteById(id);

        // eye
        try {
            boardRepository.findById(id);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("게시글 id를 찾을 수 없습니다");
        }

    }

    @Test
    public void findById_test() {
        // given
        int id = 1;

        // when
        Board board = boardRepository.findById(id);

        // eye
        System.out.println(board.getUser().getUsername());
    }

    @Test
    public void findAll_test() {
        // given

        // when
        System.out.println("1. 첫번째 조회");
        List<Board> boardList = boardRepository.findAll();
        System.out.println("userId : " + boardList.get(0).getUser().getId());
        System.out.println("=================");

        // eye
        System.out.println("2. Lazy Loading");
        System.out.println("username : " + boardList.get(0).getUser().getUsername());
        System.out.println("username : " + boardList.get(4).getUser().getUsername());

    }


    // 테스트 메서드에서는 매개변수를 사용할 수 없다.
    // 메소드명_test : 컨벤션
    @Test
    public void save_test() {
        // given (매개변수를 강제로 만들기)
        String title   = "제목1";
        String content = "내용1";

        // Board 객체 생성
        Board board = Board.builder()
                        .title(title)
                        .content(content)
                        .build();

        // when
        boardRepository.save(board);

        // eye
    }
}
