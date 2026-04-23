package Spring.ex.SpringEx.CH06.Controller;

import Spring.ex.SpringEx.CH06.Member;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    @GetMapping("/reads")
    public List<Member> getMember(){
        return List.of(
                new Member(1L, "Loo", "Loo@test.com", 11),
                new Member(2L, "Yoo", "Yoo@test.com", 12),
                new Member(3L, "Roo", "Roo@test.com", 13)
        );
    }

    @GetMapping("/{id}")
    public Member getMemberId(@PathVariable Long id){
        return  new Member(3L, "Roo", "Roo@test.com", 13);
    }
}
