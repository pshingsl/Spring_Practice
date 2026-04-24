package Spring.ex.SpringEx.CH09.User.Controller;

import Spring.ex.SpringEx.CH09.User.DTO.UserDTO;
import Spring.ex.SpringEx.CH09.User.Service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("UserController09")
@RequestMapping("/Users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 생성
    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    // 전체 조회
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    // 아이디 조회
    @GetMapping("/{id}")
    public UserDTO getId(@PathVariable Long id) {
        return userService.findById(id);
    }

    // 이름 조회
    @GetMapping("/name/{username}")
    public UserDTO getUserName(@PathVariable String username) {
        return userService.findByName(username);
    }

    // 업데이트
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody UserDTO dto) {
        userService.update(id, dto);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "성공했습니다.";
    }
}
