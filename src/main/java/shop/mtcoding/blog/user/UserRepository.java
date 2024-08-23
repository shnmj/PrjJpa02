package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception401;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;  // manager를 통해 JPQL을 사용하여 쿼리 실행

    public User findByUsername(String username) {  // service 전 test 하기 -> why?
        Query query = em.createQuery("select u from User u where u.username=:username", User.class);
        query.setParameter("username", username);

        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsernameAndPassword(String username, String password) {
        Query query = em.createQuery("select u from User u where u.username=:username and u.password=:password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            User user = (User) query.getSingleResult();
            return user;
        } catch (Exception e) {
            throw new Exception401("ID 또는 Password가 틀렸습니다."); // e.get하는 순간 클라이언트에게 전체 에러msg가 전달되기에 위험 -> 서버측에만 전달
        }
    }

    @Transactional
    public void save(User user) {
        System.out.println("담기기 전 : " + user.getId());
        em.persist(user);
        System.out.println("담긴 후 : " + user.getId());
    }

}
