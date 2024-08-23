package shop.mtcoding.blog.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(UserRepository.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByUsername_test() {
        String username = "haha";
        User user = userRepository.findByUsername(username);

        System.out.println("user = " + user);  // user = null
    }

    @Test
    public void findByUsernameAndPassword_test() {
        String username = "ssar";
        String password = "1234";
        User user = userRepository.findByUsernameAndPassword(username, password);
        System.out.println(user.getUsername());
        System.out.println(user.getEmail());
    }

    @Test
    public void save_test() {
        String username = "haha";
        String password = "1234";
        String email = "haha@nate.com";

        userRepository.save(User.builder().username(username).password(password).email(email).build());
    }
}
