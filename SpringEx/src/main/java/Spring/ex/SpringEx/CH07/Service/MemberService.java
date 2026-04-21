package Spring.ex.SpringEx.CH07.Service;

import Spring.ex.SpringEx.CH07.Member;
import Spring.ex.SpringEx.CH07.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
* 서비스는 비즈니스 로직(CRUD뿐만 아니라 어떤 조건에서 저장/수정할지 같은 규칙을 담당)을 처리하는 곳이다.
* 저장소(Repository)와 연결하여 데이터베이스에 접근한다.
*
 * 현재는 Spring Data JDBC에서는 변경 감지 기능이 없기 때문에
 * 데이터를 수정한 후 반드시 save()를 호출해야 DB에 반영된다.
 * 안그러면 데이터베이스 값이 반영이 안된다.
 *
* 추후 JPA배우면 JPA에서 변경 감지가 있어서
* 트랜잭션 안에서는 save()를 호출하지 않아도 수정이 자동 반영될 수 있다.
* */

@Service("MemberService7")
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 생성
    public Member create(Member member) {
        return memberRepository.save(member);
    }

    // 조회
    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    // 상세 조회
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }

    // 상세 이름 조회
    public Member findByName(String name) {
        return memberRepository.findByName(name);
    }

    // 수정
    public Member update(Long id, Member updateMember) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 회원이 없습니다."));

        // 콘솔에서 에러 확인용
        if (updateMember.getAge() > 0) {
            member.setAge(updateMember.getAge());
        }

        if (updateMember.getName() != null) {
            member.setName(updateMember.getName());
        }

        return memberRepository.save(member);
    }

    // 삭제
    public void deleteMember(Long id) {
        // delete from member where id =?
        memberRepository.deleteById(id);
    }
}
