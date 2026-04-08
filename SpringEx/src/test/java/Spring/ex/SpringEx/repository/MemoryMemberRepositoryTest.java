package Spring.ex.SpringEx.repository;

import Spring.ex.SpringEx.CH02.Member;
import Spring.ex.SpringEx.CH02.Repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    public void save(){
        Member member = new Member();
        member.setName("Spring");

        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        System.out.println("result: " + (result == member));
        System.out.println("result: " + (result.equals(member)));
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName(){
        Member member1 = new Member();
        member1.setName("Spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("Spring1");
        repository.save(member2);

        Member result = repository.findByName("Spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll(){
        Member member = new Member();
        member.setName("Spring1");
        repository.save(member);

        Member member2 = new Member();
        member2.setName("Spring2");
        repository.save(member2);

        List<Member> memberList = repository.findAll();

        assertThat(memberList.size()).isEqualTo(2);
    }
}
