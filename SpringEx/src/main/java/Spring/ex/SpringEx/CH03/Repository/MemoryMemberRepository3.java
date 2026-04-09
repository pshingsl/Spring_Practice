package Spring.ex.SpringEx.CH03.Repository;

import Spring.ex.SpringEx.CH03.Member3;


import java.util.*;

public class MemoryMemberRepository3 implements MemberRepository3 {

    private static Map<Long, Member3> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member3 save(Member3 member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member3> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member3> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member3> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }
}