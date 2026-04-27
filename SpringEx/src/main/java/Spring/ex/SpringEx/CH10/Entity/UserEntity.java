package Spring.ex.SpringEx.CH10.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 사용자 고유 id

    @Column(name="username", nullable = false)
    private String username; // 사용자 이름

    @Column(name="email", nullable = false)
    private String email; // 사용자 email

    @Column(name="password", nullable = false)
    private String password; // 패스워드
}
