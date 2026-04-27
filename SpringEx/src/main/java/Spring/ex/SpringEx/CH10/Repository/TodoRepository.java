package Spring.ex.SpringEx.CH10.Repository;

import Spring.ex.SpringEx.CH10.Entity.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
    List<TodoEntity> findByUserId(String userId);
}
