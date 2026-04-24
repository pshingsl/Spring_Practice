package Spring.ex.SpringEx.CH09.Post.Entity;

import Spring.ex.SpringEx.CH09.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

@Entity
@Table(name = "Post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
