package Spring.ex.SpringEx.CH08;

import lombok.Getter;
import lombok.Setter;

/*
 * User 클래스
 * - 데이터베이스 엔티티(객체)를 표현하는 도메인
 * - 실제 데이터의 역할이므로 "테이블 구조"와 동일 해야한다.
 * */
@Getter
@Setter
public class User {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private String create_at;
}
