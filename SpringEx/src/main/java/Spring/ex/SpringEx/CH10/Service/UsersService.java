package Spring.ex.SpringEx.CH10.Service;

import Spring.ex.SpringEx.CH10.Entity.UserEntity;
import Spring.ex.SpringEx.CH10.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository userRepository;

    public UserEntity create(UserEntity entity) {
        if (entity == null || entity.getEmail() == null) {
            throw new RuntimeException("Invalid arguments");
        }

        String email = entity.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already exist");
        }

        return userRepository.save(entity);
    }

    /*
    * 패스워드 암호화 적용 후
    * password: 클라이언트가 좇아하는 현재 유저에 대한 비밀번호
    * user.getPassword(): DB에 저장된 정답 비밀번호
    * matches: salt를 고려해 두 값을 비교하는 메소드
    * PasswordEncoder: security에서 제공하는 기능 비밀번호를 암호화에 쓰임
    * */
    public UserEntity getByCredentials(final String email, final String password, PasswordEncoder encoder) {
        UserEntity user = userRepository.findByEmail(email);

        if (user != null && encoder.matches(password, user.getPassword())) {
            return user; // 인증 성공 -> DB에 저장된 유저 리턴
        }
        return null; // 인증 실패 -> 해당 이메일이 없거나 비밀번호 틀림
    }
}
