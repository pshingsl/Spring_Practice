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
데이터가 생성, 저장, 처리되는 전 과정에서 정확성, 일관성, 유효성을 유지하는 상태

---

# Lombok

## Lombok
getter, setter, toString, 생성자 등을 어노테이션으로 자동 생성해주는 라이브러리

---

## @Getter

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
@Getter + @Setter + @ToString + @EqualsAndHashCode + @RequiredArgsConstructor 포함

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