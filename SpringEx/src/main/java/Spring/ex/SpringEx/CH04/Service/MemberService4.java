package Spring.ex.SpringEx.CH04.Service;

import Spring.ex.SpringEx.CH04.Member4;
import Spring.ex.SpringEx.CH04.Repository.MemberRepository4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService4 {

    private final MemberRepository4 memberRepository;

    @Autowired
    public MemberService4(MemberRepository4 memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member4 member) {
        // 같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private  void validateDuplicateMember(Member4 member){
        memberRepository.findByName(member.getName())
                .ifPresent(name -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member4> findMembers() {
        return  memberRepository.findAll();
    }

    public Optional<Member4> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
