package Spring.ex.SpringEx.CH10.Controller;

import Spring.ex.SpringEx.CH09.User.DTO.UserDTO;
import Spring.ex.SpringEx.CH10.DTO.ResponseDTO;
import Spring.ex.SpringEx.CH10.Entity.UserEntity;
import Spring.ex.SpringEx.CH10.Service.UsersService;
import Spring.ex.SpringEx.CH10.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/USERS")
public class UsersController {
    @Autowired
    private UsersService userService;

    @Autowired
    private TokenProvider tokenProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/signup")
    public ResponseEntity<?> create(@RequestBody UserDTO dto) {
        try {
            UserEntity user = UserEntity.builder()
                    .email(dto.getEmail())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .username(dto.getUsername())
                    .build();

            UserEntity registerUser = userService.create(user);
            UserDTO response = UserDTO.builder()
                    .email(registerUser.getEmail())
                    .password(registerUser.getPassword())
                    .username(registerUser.getUsername())
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
    @PostMapping("/signin")
    public ResponseEntity<?> signIn (@RequestBody UserDTO dto) {
        UserEntity user = userService.getByCredentials(dto.getEmail(), dto.getPassword(), passwordEncoder);

        if(user != null) {
            /*
             * jwt 적용전
             *   final UserDTO response = UserDTO.builder()
             *      .email(user.getEmail())
             *      .id(user.getId())
             *      .build()
             */

            // jwt 적용 후
            final String token = tokenProvider.create(user);
            final UserDTO response = UserDTO.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .token(token)
                    .build();

            return ResponseEntity.ok().body(response);
        } else  {
            // 로그인 실패
            ResponseDTO response = ResponseDTO.builder()
                    .error("로그인 실패!")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
}
}
