package Spring.ex.SpringEx.CH07.Repository;

import Spring.ex.SpringEx.CH07.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("MemberRepository7")
public interface MemberRepository extends CrudRepository<Member, Long> {

    // Select * from member
    List<Member> findAll();

    // select * from member where name = ?;
    Member findByName(String name);
}
