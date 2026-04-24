package Spring.ex.SpringEx.CH09.Post.Repository;

import Spring.ex.SpringEx.CH09.Post.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
