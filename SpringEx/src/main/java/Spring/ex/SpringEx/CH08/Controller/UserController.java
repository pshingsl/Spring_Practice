package Spring.ex.SpringEx.CH08.Controller;

import Spring.ex.SpringEx.CH08.Service.UserService;
import Spring.ex.SpringEx.CH08.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 전체 조회
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    // 특정 유저 조회
    @GetMapping("/{id}")
    public UserDTO findByUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    // 생성
    @PostMapping
    public UserDTO create(@RequestBody UserDTO dto) {
        return userService.create(dto);
    }

    // 수정
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserDTO dto) {
        dto.setId(id);
        userService.update(dto);
        return dto;
    }

    // 삭제
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        userService.delete(id);
        return "삭제 되었습니다.";
    }
}
