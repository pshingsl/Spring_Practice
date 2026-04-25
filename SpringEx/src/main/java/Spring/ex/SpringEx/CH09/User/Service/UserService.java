package Spring.ex.SpringEx.CH09.User.Service;

import Spring.ex.SpringEx.CH09.User.DTO.UserDTO;
import Spring.ex.SpringEx.CH09.User.Entity.User;
import Spring.ex.SpringEx.CH09.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserService09")
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 생성
    public UserDTO create(UserDTO dto) {
        User user = User.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();

        // 엔티티에 생성과 동시에 업데이트 생성 부분에서 save가능
        // 새로운 엔티티를 영속성 컨텍스트에 등록하고 DB에 저장하기 위해 save()을 사용
        return converToDTO(userRepository.save(user));
    }

    // 전체 조회
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();

        List<UserDTO> dtos = new ArrayList<>();

        for (User user : users) {
            UserDTO dto = converToDTO(user);
            dtos.add(dto);
        }

        return dtos;
    }

    // 아이디 조회
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저 ID의 사용자가 없습니다."));

        return converToDTO(user);
    }

    // 이름 조회
    public UserDTO findByName(String name) {
        User user = userRepository.findByUsername(name);

        if (user == null) {
            throw new RuntimeException("해당 사용자가 없습니다.");
        }

        return converToDTO(user);
    }

    // 업데이트
    @Transactional
    public void update(Long id, UserDTO dto) {

        // 1. findById로 영속성 컨텍스트가 관리하는 '영속 상태' 엔티티를 조회.(select)
        // 2. 관리 상태인 객체의 값만 변경하면, 트랜잭션이 끝날 때 JPA가 변경을 감지(Dirty Checking)하여
        //    자동으로 UPDATE 쿼리를 실행.
        // 3. 여기서 save()를 또 호출하면 내부적으로 merge 로직이 돌아 불필요한 SELECT가 추가로 발생.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
        user.update(dto.getUsername(), dto.getEmail());
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }


    private User converToEntity(UserDTO dto) {
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .build();
    }

    private UserDTO converToDTO(User user) {
        return UserDTO.builder().username(user.getUsername()).email(user.getEmail()).build();
    }

    private User converToEntityWithId(Long id, UserDTO dto) {
        return User.builder().id(id).username(dto.getUsername()).email(dto.getEmail()).build();
    }
}
