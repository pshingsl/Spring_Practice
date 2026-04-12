# Spring 정리

## Spring이란?
Spring은 Java 기반 애플리케이션 개발을 위한 백엔드 프레임워크이다.  
[확실] 객체 지향 설계를 기반으로 유지보수성과 확장성을 높이기 위한 다양한 기능 제공

---

## 스프링 프레임워크 특징
- IoC (Inversion of Control)
- DI (Dependency Injection)
- AOP (Aspect Oriented Programming)
- POJO (Plain Old Java Object)

---

## IoC (Inversion of Control, 제어의 역전)

객체 생성, 의존성 관리, 생명주기의 제어권이 개발자가 아닌 스프링 컨테이너로 넘어가는 개념

[확실] 핵심: "객체를 누가 생성하고 관리하는가" → 개발자 → 스프링

---

### IoC 적용 전

```java
class Service {
    private Repository repository = new Repository();

    public void doSomething() {
        repository.save();
    }
}

class Repository {
    public void save() {
        System.out.println("save");
    }
}
```

[확실] 직접 객체 생성 → 강한 결합

---

### IoC 적용 후

```java
@Component
class Repository {
    public void save() {
        System.out.println("save");
    }
}

@Component
class Service {

    private final Repository repository;

    @Autowired
    public Service(Repository repository) {
        this.repository = repository;
    }

    public void doSomething() {
        repository.save();
    }
}
```

[확실] 객체 생성 및 관리 → 스프링 컨테이너 담당

---

## DI (Dependency Injection, 의존성 주입)

객체가 직접 의존 객체를 생성하지 않고, 외부(스프링 컨테이너)에서 주입받는 방식

[확실] IoC를 구현하는 대표적인 방법

---

### 1. 필드 주입 (비권장)

```java
@Component
class Service {

    @Autowired
    private Repository repository;
}
```

[확실] 테스트 어려움, final 사용 불가 → 실무 비권장

---

### 2. 생성자 주입 (권장)

```java
@Component
class Service {

    private final Repository repository;

    @Autowired
   public Service(Repository repository) {
        this.repository = repository;
    }
}
```

[확실] 불변성 보장 + 테스트 용이 → 가장 권장

---

### 3. 수정자 주입

```java
@Component
class Service {

    private Repository repository;

    @Autowired
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
```

[확실] 선택적 의존성에서 사용

---

## AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)

핵심 로직과 공통 기능(로깅, 보안 등)을 분리하는 기법

---

### AOP 적용 전

```java
class Service {

    public void transfer() {
        System.out.println("로그 기록");
        System.out.println("보안 체크");

        System.out.println("계좌 이체 실행");

        System.out.println("로그 기록");
    }
}
```

[확실] 공통 로직 중복 발생

---

### AOP 적용 후

```java
@Aspect
@Component
class LoggingAspect {

    @Before("execution(* Service.*(..))")
    public void before() {
        System.out.println("로그 기록");
    }
}

@Component
class Service {

    public void transfer() {
        System.out.println("계좌 이체 실행");
    }
}
```

[확실] 공통 기능 분리 → 유지보수성 향상

---

## POJO (Plain Old Java Object)

특정 프레임워크에 의존하지 않는 순수 Java 객체

---

### POJO 적용 전

```java
class User extends SomeFrameworkClass {
}
```

[확실] 프레임워크 의존 → 변경 시 영향 큼

---

### POJO 적용 후

```java
class User {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

[확실] 순수 객체 → 재사용성, 테스트 용이성 증가

---

## Spring Boot란?

Spring을 더 쉽고 빠르게 사용할 수 있도록 지원하는 프레임워크

[확실] 자동 설정 + 내장 서버 제공

---

## Spring Boot 특징

### 1. 내장 WAS

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

[확실] Tomcat 내장 → 별도 서버 필요 없음

---

### 2. Starter 의존성

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

[확실] 라이브러리 자동 구성

---

### 3. Java 기반 설정

```java
@Configuration
class AppConfig {

    @Bean
    public Repository repository() {
        return new Repository();
    }
}
```

[확실] XML 없이 설정 가능

---

### 4. Jar 실행

```bash
java -jar app.jar
```

[확실] 단일 파일 실행 → 배포 간편