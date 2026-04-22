package Spring.ex.SpringEx.CH08.Service;

import Spring.ex.SpringEx.CH08.Repository.UserMapper;
import Spring.ex.SpringEx.CH08.User;
import Spring.ex.SpringEx.CH08.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // 전체 조회
    public List<UserDTO> getAll() {

        // 1. 모든 도메인(데이터베이스에 있는 테이블)에 있는 User객체(테이블)의 정보를 가져옴
        List<User> users = userMapper.findAll();

        // 2. 새로운 DTO 객체를 생성
        List<UserDTO> userDTOS = new ArrayList<>();

        // 3. 반복문을 이용해서 User객체(테이블)를 DTO로 변환하고 리스트에 추가
        for (User user : users) {
            UserDTO userDTO = convertToDto(user);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    // 특정 아이디 조회
    public UserDTO findById(Long id) {
        User user = userMapper.findById(id);

        return convertToDto(user);
    }

    // 생성
    public UserDTO create(UserDTO dto) {
        User user = convertToEntity(dto);
        userMapper.insert(user);
        return convertToDto(user);
    }

    // 수정
    public void update(UserDTO dto) {
        User user = convertToEntity(dto);
        userMapper.update(user);
    }

    // 삭제
    public void delete(Long id) {
        userMapper.delete(id);
    }


    /*
    * convertToDto
    * 데이터베이스와 직접 연동되는 엔티티(Entity) 객체를 클라이언트(프론트(view)나
    * API 응답에 적합한 데이터 전송 객체(DTO)로 변환하는 역할을 사용자 정의 메서드
    * -> 데이터베이스에서 가져온 데이터를 화면에 보여주기 좋은 형태로 바꾸는 메서드
    * 백엔드에서 프론트로 데이터를 변환할 때 사용한다. -> DB 데이터 -> 화면용 데이터로 변환
    * Entity -> DTO로 변경
    *
    * 특징
    * 데이터베이스 스키마와 직접 관련된 엔티티(테이블)객체를 외
    * 부로 노출하면 보안상 위험할 수 있고, 불필요한 데이터가 전달될 수 있다.
    *
    * API 응답에 필요한 데이터만 DTO에 담아 전달할 수 있다.
    *
    * 화면에서 필요로 하는 구조로 변환하여 전달할 수 있다.
    * */
    private UserDTO convertToDto(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAge(user.getAge());

        return dto;
    }

    /*
    * convertToEntity
    * 주로 DTO(Data Transfer Object)를 JPA 엔티티(Entity)객체로 변환하는 메서드 과정
    * - > 크라이언트에서 받은 데이터르 데이터베이스에 저장할 수 있는 형태로 바꾸는 메서드
    * DTO -> Entity로 변경
    * 프론트에서 받은 데이터를 엔티티로 변환해야 할 때 사용한다.
    * -> 화면 데이터 -> DB 저장용 데이터로 변환
    *
    * 특징
    * 프론트에서 받은 데이터로 기록을 데이터베이스에 저장할 수 있기 때문이다.
    *
    * Entity는 데이터베이스 구조와 직접 매핑이되므로, 도메인 로직과 외부 입력(API 요청 등)을 분리하여 안정성을 높일 수 있다.
    *
    * 별도의 매핑 파이브러리없이, DTO 클래스 내부에 정의하여 코드가 직관적이다.
    *
    * 데이터변환 시점에 데이터를 검증하는 로직을 포함할 수 있다.
    * */
    private User convertToEntity(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAge(dto.getAge());

        return user;
    }

}
