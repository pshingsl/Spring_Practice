package Spring.ex.SpringEx.CH03.Repository;

import Spring.ex.SpringEx.CH03.Member3;

import java.util.List;
import java.util.Optional;

public interface MemberRepository3 {
    Member3 save(Member3 member);
    Optional<Member3> findById(Long id);
    Optional<Member3> findByName(String name);
    List<Member3> findAll();
}
