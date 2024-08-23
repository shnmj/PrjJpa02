package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.core.error.ex.Exception400;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO joinDTO){
        User oldUser = userRepository.findByUsername(joinDTO.getUsername()); // 기존 회원

//        정상 로직은 ( if else에서 == 같은 정상적인) 밑에 따로 빼고
//        비정상 로직 ( != 같은) 을 위에 작성해서 하나씩 필터링
        if(oldUser != null){
            throw new Exception400("이미 존재하는 회원이름입니다.");
        }

        userRepository.save(joinDTO.toEntity());

    }

    public User 로그인(UserRequest.LoginDTO loginDTO){
        User user = userRepository.findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());
        return user;
    }
}
