package Spring.ex.SpringEx.CH04.Controller;


import Spring.ex.SpringEx.CH04.Member4;
import Spring.ex.SpringEx.CH04.Service.MemberService4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller("memberControllerV4")
public class MemberController4 {
    private final MemberService4 memberService;

    @Autowired
    public MemberController4(MemberService4 memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm memberForm) {
        Member4 member4 = new Member4();
        member4.setName(memberForm.getName());

        memberService.join(member4);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String read(Model model) {
        List<Member4> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
