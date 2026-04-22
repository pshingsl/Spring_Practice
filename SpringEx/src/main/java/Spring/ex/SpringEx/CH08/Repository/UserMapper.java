package Spring.ex.SpringEx.CH08.Repository;

import Spring.ex.SpringEx.CH08.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/*
* 매퍼 클래스 해당 인터페이스가 MyBatis mapper임을 나타낸다/
* 해당 매퍼 클래스에서 스프링에서 제공해주는 SQL 어노테이션으로 쿼리를 작성이 가능하다
* Java 코드 내에서 SQL문을 직접 볼 수 있어서 즉각적인 이해가 가능하다.
* 복잡한 쿼리를 작성할 수 있지만 mapper인터페이스의 코드 가독성이 안 좋아진다.
*
* XML방식으로는 지금 아래의 쿼리를 지우고 메소드만 정의한다.
* 그 다음 resource/mapper/mapper명으로 xml파일을 만들어서 해당 메소드의 쿼리를 정의한다.
* XML의 방식으로는 복잡한 쿼리를 작성할 수 있으며 Mapper인터페이스의 코드는 간결해진다.
* */
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users")
    List<User> findAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);

    @Insert("INSERT INTO users (name, email, age) VALUES (#{name}, #{email}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Update("UPDATE users SET name = #{name}, email = #{email}, age = #{age} WHERE id = #{id}")
    void update(User user);

    @Delete("DELETE FROM users WHERE id = #{id}")
    void delete(Long id);
}
