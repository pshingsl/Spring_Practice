package Spring.ex.SpringEx.CH09.User.Service;

import Spring.ex.SpringEx.CH09.User.DTO.UserDTO;
import Spring.ex.SpringEx.CH09.User.Entity.User;
import Spring.ex.SpringEx.CH09.User.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = converToEntity(dto);

        User savedUser = userRepository.save(user);

        return converToDTO(savedUser);
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
    public void update(Long id, UserDTO dto) {
        User user = converToEntityWithId(id, dto);
        userRepository.save(user);
    }

    // 삭제
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
