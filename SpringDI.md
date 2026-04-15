# Spring DI (Dependency Injection)

---

## DI (Dependency Injection, 의존성 주입)
객체가 필요로 하는 의존 객체를 직접 생성하지 않고 외부(Spring Container)에 주입받는 기술이다.

즉, 객체를 직접 생성하지 않고 외부에서 주입 받아 사용한다.

DI를 사용하면 객체 간 결합도를 낮추어 **코드의 재사용성과 유지보수성**이 좋아진다.

---

## 의존성 (Dependency)
한 객체(클래스)가 다른 객체의 기능을 사용할 때 발생하는 결합 관계이다.

구성 요소의 의존관계는 소스코드 내부가 아닌 외부(스프링 컨테이너)를 통해 정의된다.

즉, 외부에서 객체를 주입 받아야 한다.

- 파라미터
- 리턴 값
- 지역 변수

등을 통해 다른 객체를 참조하는 것이 의존성이다.

### 예시
A 객체가 동작하기 위해 B 객체가 필요하다면  
→ A는 B에 의존한다고 표현한다.

---

## 의존성이 중요한 이유
게시판 기능에서 데이터를 파일이 아닌 DB로 저장하도록 변경한다고 가정

→ 서비스 코드 수정 필요  
→ 의존하는 클래스가 많을수록 수정 범위 증가

이 문제를 해결하기 위해 DI 사용

→ 객체 생성 책임을 외부로 분리  
→ 변경 영향 최소화

의존성 주입을 사용하면 객체를 **직접 생성하지 않고 외부에서 주입**받는다.  
구현체가 변경되더라도 사용하는 코드(서비스 코드)를 수정하지 않아도 된다.

즉, 객체 간 **결합도가 낮아지고 그 결과 유지보수성과 재사용성이 향상**된다

```java
public interface NotificationService {
    void send();
}

public class EmailNotificationService implements NotificationService {
    @Override
    public void send() {
        System.out.println("이메일에서 보냄");
    }
}

public class SMSNotificationService implements NotificationService {
    @Override
    public void send() {
        System.out.println("SMS에서 보냄");
    }
}

public class OrderService {
    
    private NotificationService notificationService;
    
    public OrderService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    public void order() {
        System.out.println("주문쪽에서 실행 중 입니다.");
        notificationService.send();
    }
    
    public class Main{
        public static void main(String[] args) {
            NotificationService emailService= new EmailNotificationService();
            OrderService email = new OrderService(emailService);
            email.order();
            System.out.println("-----");
            
            NotificationService SMSSservice = new SMSNotificationService();
            OrderService SMS = new OrderService(SMSSservice);
            SMS.order();
        }
    }
}

```

계층 구조
```
[ Main ] ----------------┐ (객체 생성 및 주입)
|                     |
v                     v
[ OrderService ] ----> [ NotificationService (Interface) ]
^
| (구현/implements)
---------------------------------
|                               |
[ EmailNotificationService ]    [ SNSNotificationService ]
```

---

## Spring Boot에서 DI 구현 핵심

### Bean (빈)
스프링 컨테이너에 의해 관리되는 자바 객체

- 스프링 컨테이너가 객체 생성 및 관리
- 객체 간 의존관계도 함께 관리

---

## 빈 등록 방법

### 1. XML 직접 등록
```xml
<beans>
    <bean id="memberService" class="com.example.MemberService"/>
</beans>
```

---

### 2. @Bean 사용 (Java Config)
```java
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService();
    }
}
```

---

### 3. 컴포넌트 스캔 (@Component 계열)
```java
@Component
public class MemberService {
}
```

```java
@Service
public class MemberService {
}
```

```java
@Repository
public class MemberRepository {
}
```

```java
@Controller
public class MemberController {
}
```

---

## IoC 컨테이너 (Inversion of Control Container)
객체의 생성, 생명주기 관리, 의존성 주입을 담당하는 스프링의 핵심 컴포넌트

개발자가 아닌 스프링이 객체 생성과 관리를 담당한다.

→ 제어의 역전 (IoC)

---

## 의존성 주입 방법

### 1. 필드 주입
클래스의 필드에 직접 주입

```java
@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;
}
```

---

### 2. 생성자 주입 (권장 방식)
생성자를 통해 의존성 주입

- 객체 생성 시 1회 호출 보장
- 필수 의존성 강제 가능
- 불변성 유지 가능

```java
@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

---

### 3. 세터 주입
Setter 메서드를 통해 주입

- 선택적 의존성
- 변경 가능성 있는 경우 사용

```java
@Controller
public class MemberController {

    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

---

## 생성자 주입을 사용하는 이유

### 1. 불변성 유지
의존 관계는 변경될 일이 거의 없음

→ Setter 사용 시 불필요한 변경 가능성 존재

---

### 2. 테스트 용이성
생성자 주입은 순수 자바 코드로 테스트 가능

→ 의존성 누락 시 컴파일 시점 오류 발생

---

### 3. final 키워드 사용 가능
```java
private final MemberService memberService;
```

→ 반드시 초기화 필요  
→ 안정성 증가

---

### 4. Lombok과 함께 사용
```java
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
}
```

→ 생성자 자동 생성

---

## 참고
- 실무에서는 Controller, Service, Repository는 컴포넌트 스캔 사용
- 설정이 필요한 경우 @Bean 사용  