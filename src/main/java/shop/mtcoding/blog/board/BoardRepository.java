package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception404;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Repository // @Repository를 붙이면 스프링이 new를 해서 IoC(컬렉션 List자료형 같은거) 에 저장한다.
public class BoardRepository {

    // IoC에 있는 객체를 찾아온다.
    private final EntityManager em;

    @Transactional
    public void updateById(String title, String content, int id) {
        Query query = em.createNativeQuery("update board_tb set title = ?, content = ? where id = ?");
        query.setParameter(1, title);
        query.setParameter(2, content);
        query.setParameter(3, id);

        query.executeUpdate();
    }

    @Transactional
    public void deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);
        query.executeUpdate();
    }

    public Board findByIdV2(int id) {
        Query query = em.createNativeQuery("select bt.id, bt.title, bt.content, bt.user_id, bt.created_at, ut.id u_id, ut.username, ut.password, ut.email, ut.created_at u_created_at from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = ?");
        query.setParameter(1, id);
        Object[] obs = (Object[]) query.getSingleResult();

        System.out.println(obs[0]);
        System.out.println(obs[1]);
        System.out.println(obs[2]);
        System.out.println(obs[3]);
        System.out.println(obs[4]);
        System.out.println(obs[5]);
        System.out.println(obs[6]);
        System.out.println(obs[7]);
        System.out.println(obs[8]);
        System.out.println(obs[9]);

//        1
//        제목1
//        내용1
//        1
//        2024-08-21 12:49:35.197432
//        1
//        ssar
//        1234
//        ssar@nate.com
//        2024-08-21 12:49:35.194432
        Board board = new Board();
        User user = new User();
        board.setId((Integer) obs[0]);
        board.setTitle((String) obs[1]);
        board.setContent((String) obs[2]);
        board.setCreatedAt((Timestamp) obs[4]);

        user.setId((Integer) obs[3]);
        user.setUsername((String) obs[6]);
        user.setPassword((String) obs[7]);
        user.setEmail((String) obs[8]);
        user.setCreatedAt((Timestamp) obs[9]);

        board.setUser(user);

        return board;
    }

    public Board findByIdV3(int id) {
        // select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = 1
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);
        try{
            Board board = (Board) query.getSingleResult(); // No Result Exception
            return board;
        }catch (Exception e){
            throw new Exception404("게시글이 존재하지 않습니다");
        }

    }


    public Board findById(int id) {
        // select * from board_tb bt inner join user_tb ut on bt.user_id = ut.id where bt.id = 1
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id = :id", Board.class);
        query.setParameter("id", id);
        try {
            Board board = (Board) query.getSingleResult();
            return board;
        } catch (Exception e) {
            e.printStackTrace();
            // Exception을 내가 잡은것 까지 배움 - 처리 방법은 v2에서 배우기
            throw new Exception404("게시글 id를 찾을 수 없습니다");
           // return null;
        }
    }

    public List<Board> findAll() {
        Query query = em.createQuery("select b from Board b order by b.id desc", Board.class);
        List<Board> boardList = query.getResultList();
        return boardList;
    }

    // insert 하기
    @Transactional
    public void save(Board board) {
        em.persist(board);
    }

}
