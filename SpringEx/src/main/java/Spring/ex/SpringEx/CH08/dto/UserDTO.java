package Spring.ex.SpringEx.CH08.dto;

import lombok.*;

import java.util.LinkedHashMap;

/*
 * DTO(Data To Object)
 * - 서버와 클라이언트 사이에서 데이터를 주고 받을 때 사용하는 객체
 * - 데이터베이스의 전체 정보를 그대로 보내지 않고, 필요한 데이터만 골라서 전달하기 위해 사용한다.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String  name;
    private String email;
    private Integer age;

    private UserDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
    }

    public static class Builder {
        private Long id;
        private String  name;
        private String email;
        private Integer age;

//        public Builder id(Long id) {
//            this.id = id;
//            return this;
//        }
//
//        public Builder name(String name) {
//            this.name = name;
//            return this;
//        }
//
//        public Builder email(String email) {
//            this.email = email;
//            return this;
//        }
//
//        public Builder age(Integer age) {
//            this.age = age;
//            return this;
//        }
//
//        public UserDTO build() {
//            return new UserDTO(this);
//        }
    }

}

/*
* @Builder란 해당 믈래스에 빌더 클래스르 자동으로 생성한다.
* 객체 생성과 속성 설정을 담당하며, 메서드 체인을 통해 속성값을 설정할 수 있다.
* 특징
* 객체 생성과정 단순화
* 가독성과 유연성 향상시킨다
* 선택적 매개변수 처리가능
* 생성자의 인자 순서를 신경 쓰지 않아도 된다.
* */