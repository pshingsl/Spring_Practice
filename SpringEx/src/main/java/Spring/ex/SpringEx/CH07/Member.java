package Spring.ex.SpringEx.CH07;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("member")
@Getter
@Setter
public class Member {
    @Id
    private Long id;
    private String name;
    private int age;

}
