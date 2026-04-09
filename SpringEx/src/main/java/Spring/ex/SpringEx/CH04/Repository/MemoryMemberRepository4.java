package Spring.ex.SpringEx.CH04.Repository;

import Spring.ex.SpringEx.CH04.Member4;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository4 implements MemberRepository4 {

    private static Map<Long, Member4> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member4 save(Member4 member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member4> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member4> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member4> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}