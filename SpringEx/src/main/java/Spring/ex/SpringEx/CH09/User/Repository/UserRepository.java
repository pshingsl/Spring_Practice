package Spring.ex.SpringEx.CH09.User.Repository;

import Spring.ex.SpringEx.CH09.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepository09")
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
