package Spring.ex.SpringEx.CH03.Service;

import Spring.ex.SpringEx.CH03.Member3;
import Spring.ex.SpringEx.CH03.Repository.MemberRepository3;

import java.util.List;
import java.util.Optional;

public class MemberService3 {

    private final MemberRepository3 memberRepository;

    public MemberService3(MemberRepository3 memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member3 member) {
        // 같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private  void validateDuplicateMember(Member3 member){
        memberRepository.findByName(member.getName())
                .ifPresent(name -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member3> findMembers() {
        return  memberRepository.findAll();
    }

    public Optional<Member3> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
