package Spring.ex.SpringEx.CH07.Controller;

import Spring.ex.SpringEx.CH07.Member;
import Spring.ex.SpringEx.CH07.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* 컨트롤러는 클라이언트의 요청을 받아 해당 요청에 맞는 결과를 전달하는 곳이다.
*
* @RestController를 이용해서 데이터를 JSON 형태로 바로 응답할 수 있다. 추후에 프론트와도 연동이 가능
*
* @RequestBody를 통해 클라이언트가 보내 JSON 데이터를 자바 객체로 변환될 떄 사용할 수 있다.
*
 * ※ URL 매핑 기준
 * Spring은 "경로(URL)"로 요청을 구분한다 -> 같은 경로가 있으면 충돌이 발생하여 컨테이너가 어떤 메서드을 처리할지 모른다,
 *
*  * 예시:
* /members/{id}        → ID로 조회
* /members/name/{name} → 이름으로 조회
*
* 경로가 다르기 때문에 서로 충돌하지 않는다.
* */
@RestController("MemberController7")
@RequestMapping("/ch07/members")
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public List<Member> getAll() {
        return memberService.getAll();
    }

    @GetMapping("/{id}")
    public Member getById(@PathVariable Long id) {
        return memberService.findById(id);
    }

    @GetMapping("/name/{name}")
    public Member getByName(@PathVariable String name){
        return memberService.findByName(name);
    }

    @PostMapping
    public Member create(@RequestBody Member member) {
        return memberService.create(member);
    }

    @PutMapping("/{id}")
    public Member update(@PathVariable Long id, @RequestBody Member member) {
        return memberService.update(id, member);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        memberService.deleteMember(id);
        return "성공적으로 삭제되었습니다";
    }
}
