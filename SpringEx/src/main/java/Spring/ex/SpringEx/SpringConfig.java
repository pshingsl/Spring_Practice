package Spring.ex.SpringEx;

import Spring.ex.SpringEx.CH03.Repository.MemberRepository3;

import Spring.ex.SpringEx.CH03.Repository.MemoryMemberRepository3;
import Spring.ex.SpringEx.CH03.Service.MemberService3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService3 memberService3(){
        return new MemberService3(memberRepository3());
    }

    @Bean
    public MemberRepository3 memberRepository3(){
        return new MemoryMemberRepository3();
    }

}
