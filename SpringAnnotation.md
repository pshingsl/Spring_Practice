# Spring Annotation 정리

---

## Spring 어노테이션
자바 코드에 추가하여 특별한 의미와 기능(메타데이터)을 부여하는 표식이다.

컴파일러나 런타임에 이 정보를 읽어 스프링이 자동으로 빈(Bean) 등록, 의존성 주입(DI), AOP 등의 처리를 수행한다.

XML 설정 없이 깔끔하고 생산적인 개발이 가능하다.

---

## 어노테이션 장점과 사용법

### 장점
- 코드량 감소
- 유지보수 용이
- 생산성 증가

### 동작 흐름
1. 개발자가 어노테이션을 코드에 선언
2. 클래스/메서드에 어노테이션 적용
3. 스프링이 실행 시 스프링이 메타데이터를 읽어 기능 수행
4. 빈 등록, DI, AOP 등의 기능 수행

---

## Spring Boot Annotation 종류

---

### @SpringBootApplication
스프링 부트 애플리케이션의 시작점

- 자동 설정 (@EnableAutoConfiguration)
- 컴포넌트 스캔(@ComponentScan)
- 설정 클래스(@Configuration)

```java
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

---

### @Component
스프링 컨테이너가  자동으로 빈으로 등록하는 기본 어노테이션

컴포너트 스캔 대상이 되며, 스프링이 객체 생성 및 생명주기를 관리한다.
```java
@Component
public class NotificationManager {

    public void sendNotification(String msg) {
        System.out.println(msg);
    }
}
```

---

### @ComponentScan
개발자가 작성한 클래스를 스프링 컨테이너가 자동으로 탐색하여 빈(Bean)으로 등록하는 가장 기본적인 애노테이션이다.
@ComponentScan 기능에 의해 런타임 시 자동으로 감지되며 Ioc를 통해 스프링이 직접 객체를 생명주기를 관리한다.
@Repository, @Service, @Controller의 기본이 되는 애노테이션


```java
@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class DemoApplication {
}
```

---

### @Controller
스프링 MVC에서 웹 요청(HTTP Request)을 받아 처리하고, 주로 HTML 뷰(View)를 반환하는 전통적인 컨트롤러 클래스를 정의하는 애노테이션이다.

```java
@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // 템플릿 반환
    }
}
```

---

### @ResponseBody
스프링 프레임워크에서 자바 객체를 HTTP  요청의 응답 본문(Response Body) 으로 변환하여 
클라이언트에게 전송하는데 사용한다.

```java
@Controller
public class ApiController {

    @ResponseBody
    @GetMapping("/api/text")
    public String text() {
        return "hello";
    }
}
```

---

### @RestController
@Controller + @ResponseBody
RESTful 웹 서비스를 구축할 때 JSON or XML형식의 데이터를 직접 반환하기 위해 사용한다.

뷰(view) 대신 객체를 HTTP 응답 바디(Body)에 바로 작성하여 클라이언트에게 데이터를 전달 하는 데 특화된 컨트롤러이다.


```java
@RestController
public class ApiController {

    @GetMapping("/api/user")
    public User user() {
        return new User("kim", 20);
    }
}
```

---

### @Service
비즈니스 로직 처리

여기서 비즈니스 로직은 사용자의 요청에 따라서 DB에 접근하여 데이터를 추가, 삭제, 수정, 선택과 같은 요청을 의미한다.

```java
@Service
public class MemberService {

    public String join() {
        return "회원가입";
    }
}
```

---

### @Repository
엔티티에 의해 생서오딘 데이터베이스 테이블에 접근하는 메서드집합이다.

DB에서 CRUD와 같은 명령을 하게 되며, 간단하게 DB접근이 가능한 객체라고 생각하면된다.

```java
@Repository
public class MemberRepository {

    public void save() {
        // DB 저장 로직
    }
}
```

---

### @Autowired
스프링 컨테이너에 등록된 빈을 자동으로 의존성 주입해주는 애노테이션이다.

타입을 기준으로 빈을 검색하여 필드, 세터, 생성자에 주입하며 코드의 간결성과 느슨한 결합을 지원한다.

주로 생성자에 사용한다.


```java
@Service
public class MemberService {

    @Autowired
    private MemberRepository repository;
}
```

---

### @RequestMapping
웹 요청(URL)을 특정 컨트롤러(Controller)의 메서드와 매핑하는 핵심 어노테이션이다.

클라이언트 요청 URL, HTTP 메서드(GET, POST 등)에 따라 적절한 처리 로직을 연결하며, 클래스 메서드 단위에
선언하여 범용적인 요청 처리를 담당한다.



```java
@Controller
@RequestMapping("/users")
public class UserController {

    @RequestMapping("/list")
    public String list() {
        return "list";
    }
}
```

---

### @RequestParam
쿼리  파라미터(URL 뒤에 ?key=value) 또는 HTML 폼 데이터르 컨트롤러 메서드의 매개변수 자동으로 바인딩해주는 애노테이션이다.

```java
@GetMapping("/search")
public String search(@RequestParam String keyword) {
    return keyword;
}
```

---

### @PathVariable
URL 경로(Path)의 일부를 메서드의 파라미터로 직접 바인딩하는 애노테이션이다.

REST API에서 리소스의 식별자(ID)를 URL에 담아 요청할 때 주로 사용하며, URL 경로 중 중괄호 {}로 둘러싸인 변수 값을 가져온다.


```java
@GetMapping("/users/{id}")
public String getUser(@PathVariable Long id) {
    return "user id = " + id;
}
```

---

### @ModelAttribute
폼 데이터 → 객체 바인딩

```java
@PostMapping("/user")
public String saveUser(@ModelAttribute User user) {
    return user.getName();
}
```

---

### @Bean
스프링 컨테이너가 관리하는 Java 객체(Bean)를 수동으로 등록하기 위한 어노테이션이다.

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

### @Configuration
자바 기반의 설정 클래스를 정의하는 애노테이션이다. 

1개 이상의 @Bean을 등록하여 스프링 컨테이너에 객체(Bean)를 수동으로 생성하고 의존성 주입(DI)하는 역할을 한다.

내부적으로 싱글톤(Singleton)을 보장하며, 스프링 빈 설정의 핵심적인 메타데이터 역할을 수행한다.


```java
@Configuration
public class AppConfig {

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepository();
    }
}
```