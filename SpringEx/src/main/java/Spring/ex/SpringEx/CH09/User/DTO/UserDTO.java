package Spring.ex.SpringEx.CH09.User.DTO;

import Spring.ex.SpringEx.CH09.Post.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String token;
    private String username;
    private String email;
    private String password;
}
