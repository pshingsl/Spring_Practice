# Spring DTO, DAO, VO + Lombok 정리

---

## DTO (Data Transfer Object)
데이터 전송 객체로, 계층 간(Controller, Service, Repository) 데이터를 전달하기 위한 순수 데이터 객체이다.

로직을 포함하지 않고 필드와 getter/setter로 구성되며, DB 엔티티와 분리하여 보안성과 유연성을 확보한다.

### 예시 코드
```java
public class MemberDTO {

    private String name;
    private int age;

    public MemberDTO() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

---

## DAO (Data Access Object)
데이터베이스에 직접 접근하여 CRUD 작업을 수행하는 객체이다.

비즈니스 로직과 데이터 접근 로직을 분리하여 유지보수성을 높이고, JDBC 등의 복잡성을 숨긴다.

### 예시 코드
```java
@Repository
public class MemberDAO {

    public void save(MemberDTO member) {
        // DB 저장 로직
    }

    public MemberDTO findById(Long id) {
        return new MemberDTO();
    }
}
```

---

## VO (Value Object)
불변성과 값 자체의 동등성을 가지는 객체이다.

모든 필드 값이 같으면 동일한 객체로 판단하며, Setter 없이 생성자로만 값을 초기화하여 데이터 무결성을 유지한다.

### 예시 코드
```java
public class Money {

    private final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
```

---

## 참고

### 데이터 무결성
데이터가 생성, 저장, 처리되는 전 과정에서 정확성, 일관성, 유효성을 유지하여 오류나 손상 없이 믿고
사용할 수 있는 상태를 의미한다. 

데이터의 신뢰성을 보장하는 핵심요소로, 잘못된 정보 입력을 방지하여 제약 조건(개체/참조/도메인 등)을 통해 관리된다.

---

# Lombok 

자바에서 반복적으로 작성해야 하는  
getter, setter, 생성자, toString 등의 코드를

어노테이션을 통해 자동 생성해주는 라이브러리이다.

→ 보일러플레이트 코드를 줄여 생산성을 높인다.

---

## @Getter
클래스의 필드에 대한 getter 메서드를 자동 생성

DTO나 Entity 클래스 상단에 선언하여 편리하게 값을 조회할 수 있으며, 데이터 은닉과 접근 제어를 위해 필수적으로 사용한다.

→ 외부에서 값을 조회할 수 있도록 제공

### 적용 전
```java
public class Member {

    private String name;

    public String getName() {
        return name;
    }
}
```

### 적용 후
```java
@Getter
public class Member {
    private String name;
}
```

---

## @Setter
필드 값을 수정할 수 있는 setter 메서드 생성

→ 객체 생성 이후 값 변경 가능

### 적용 전
```java
public class Member {

    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
```

### 적용 후
```java
@Setter
public class Member {
    private String name;
}
```

---

## @Data

JPA, MongoDB 등 다양한 데이터 저장소에 대한 데이터 접근 계층을 추상화하여, 일관되고 
간편한 API를 제공하는 스프링 프레임워크의 하위 프로젝트이다. 

CRUD작업을 단순화하고, 보일러 플레이트 코드를 줄여 데이터 관리 효율성을 높인다.


다음 기능을 한 번에 제공하는 종합 어노테이션

- @Getter
- @Setter
- @ToString
- @EqualsAndHashCode
- @RequiredArgsConstructor

→ DTO에서 주로 사용

### 적용 전
```java
public class Member {

    private String name;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

### 적용 후
```java
@Data
public class Member {
    private String name;
}
```

---

## @ToString
객체의 필드 값을 문자열 형태로 출력하는 toString() 메서드를 자동 생성

→ 디버깅 및 로깅 시 객체의 내용 확일할때 필수적이다. 출력에 사용

### 적용 전
```java
public class Member {

    private String name;

    @Override
    public String toString() {
        return "Member{name='" + name + "'}";
    }
}
```

### 적용 후
```java
@ToString
public class Member {
    private String name;
}
```

---

## @Builder

필드가 많거나 복잡한 생성자 대신 가독성 높고 유연한 빌더 패턴 코드를 자동으로 생성해준다.

객체 생성 시 필드 순서에 의존하지 않고 명시적으로 값을 설정하여 코드의 유지보수성을 크게 향상시킨다.

빌더 패턴을 자동 생성


### 적용 전
```java
public class Member {

    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### 적용 후
```java
@Builder
public class Member {

    private String name;
    private int age;
}

// 사용
Member member = Member.builder()
    .name("kim")
    .age(20)
    .build();
```

---

## @NoArgsConstructor
파라미터가 없는 기본 생성자 생성

→ JPA, DTO에서 필요

### 적용 전
```java
public class Member {

    public Member() {}
}
```

### 적용 후
```java
@NoArgsConstructor
public class Member {
}
```

---

## @AllArgsConstructor
모든 필드를 파라미터로 받는 생성자 생성

### 적용 전
```java
public class Member {

    private String name;
    private int age;

    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### 적용 후
```java
@AllArgsConstructor
public class Member {

    private String name;
    private int age;
}
```

---

## @RequiredArgsConstructor
초기화되지 않은 final 또는 @NonNull 필드만 매개변수로 받는 생성자를 자동으로 생성해준다.

→ 생성자 주입(DI)에 많이 사용

### 적용 전
```java
public class Member {

    private final String name;

    public Member(String name) {
        this.name = name;
    }
}
```

### 적용 후
```java
@RequiredArgsConstructor
public class Member {

    private final String name;
}
```
---

## 참고

### 보일러플레이트 코드
반복적으로 작성되는 정형화된 코드

예: getter, setter, 생성자 등