package Spring.ex.SpringEx.CH09.Post.Entity;

import Spring.ex.SpringEx.CH09.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Length;

@Entity
@Table(name = "Post")
@Getter
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

    public void update(String title, String content) {
        validate(title, content);
        this.title = title;
        this.content = content;
    }

    private void validate(String title, String content) {
        if (title == null || title.length() > 30) {
            throw new IllegalArgumentException("제목 오류");
        }

        if (content.length() > 30 || content == null) {
            {
                throw new IllegalArgumentException("본문 오류");
            }
        }
    }
}
