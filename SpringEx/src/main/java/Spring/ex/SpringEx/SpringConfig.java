package Spring.ex.SpringEx;

import Spring.ex.SpringEx.CH05.Repository.JdbcTemplateMemberRepository;
import Spring.ex.SpringEx.CH05.Repository.MemberRepository;
import Spring.ex.SpringEx.CH05.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository(){
        // return new MemoryMemberRepository();
        return new JdbcTemplateMemberRepository(dataSource);
    }

}
