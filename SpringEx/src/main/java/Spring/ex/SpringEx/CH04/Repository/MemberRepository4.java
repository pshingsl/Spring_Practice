package Spring.ex.SpringEx.CH04.Repository;

import Spring.ex.SpringEx.CH04.Member4;

import java.util.List;
import java.util.Optional;

public interface MemberRepository4 {
    Member4 save(Member4 member);
    Optional<Member4> findById(Long id);
    Optional<Member4> findByName(String name);
    List<Member4> findAll();
}
