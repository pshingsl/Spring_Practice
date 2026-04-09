package Spring.ex.SpringEx.Service;

import Spring.ex.SpringEx.CH02.Member;
import Spring.ex.SpringEx.CH02.Repository.MemberRepository;
import Spring.ex.SpringEx.CH02.Repository.MemoryMemberRepository;

import java.util.List;
import java.util.Optional;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private  void validateDuplicateMember(Member member){
        memberRepository.findByName(member.getName())
                .ifPresent(name -> {
                    throw new IllegalArgumentException("이미 존재하는 회원입니다.");
                });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return  memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
