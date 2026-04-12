# Spring 핵심 개념 정리

---

## Spring이란?

Spring은 Java 기반 애플리케이션 개발을 위한 백엔드 프레임워크이다.  
주로 웹 애플리케이션 개발에 사용되며, 객체 지향 설계를 기반으로 유지보수성과 확장성을 높이기 위한 다양한 기능을 제공한다.

---

## 스프링 프레임워크 특징

- IoC (Inversion of Control)
- DI (Dependency Injection)
- AOP (Aspect Oriented Programming)
- POJO (Plain Old Java Object)

---

## IoC (Inversion of Control, 제어의 역전)

객체 생성, 의존성 관리, 생명주기의 제어권이 개발자가 아닌 스프링 컨테이너로 넘어가는 개념

- 개발자가 객체를 직접 생성하지 않음
- 스프링 컨테이너가 객체를 생성하고 관리

### 스프링 컨테이너
객체(Bean)를 생성하고 관리하며 의존성을 주입하는 역할

---

### IoC 적용 전

```java
class Service {

    private Repository repository = new Repository();

    public void logic() {
        repository.save();
    }
}

class Repository {
    public void save() {
        System.out.println("save");
    }
}
```

→ 객체를 직접 생성 → 강한 결합

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

    public void logic() {
        repository.save();
    }
}
```

→ 객체 생성 및 관리 → 스프링 컨테이너 담당

---

## DI (Dependency Injection, 의존성 주입)

객체가 필요한 의존 객체를 직접 생성하지 않고 외부(스프링 컨테이너)에서 주입받는 방식

- IoC를 구현하는 대표적인 방법
- 결합도 감소, 재사용성 증가, 테스트 용이성 확보

---

### 1. 필드 주입 (비권장)

```java
@Component
class Service {

    @Autowired
    private Repository repository;
}
```

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

---

### 3. 수정자 주입 (Setter)

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

---

## AOP (Aspect Oriented Programming, 관점 지향 프로그래밍)

핵심 로직과 공통 기능을 분리하여 관리하는 프로그래밍 기법

- 핵심 관심사: 비즈니스 로직
- 공통 관심사: 로깅, 보안, 트랜잭션

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

→ 공통 로직 중복 발생

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

→ 공통 기능 분리 → 유지보수성 향상

---

## POJO (Plain Old Java Object)

특정 프레임워크에 의존하지 않는 순수 Java 객체

---

### POJO 적용 전

```java
class User extends SomeFrameworkClass {
}
```

→ 특정 기술에 종속됨

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

→ 순수 객체 → 재사용성, 테스트 용이성 증가

---

## Spring Boot란?

Spring 프레임워크를 더 쉽고 빠르게 사용할 수 있도록 도와주는 확장 프레임워크

- 복잡한 설정(XML, 서버 설정 등)을 최소화
- 자동 설정(Auto Configuration) 제공

---

## Spring Boot 특징

### 1. 내장 WAS

- Tomcat, Jetty 등 내장
- 별도 서버 설치 없이 실행 가능

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

---

### 2. Starter 의존성

```gradle
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
}
```

- 필요한 라이브러리 묶음 제공

---

### 3. 설정 간소화

- XML 대신 Annotation / Java Config 사용

```java
@Configuration
class AppConfig {

    @Bean
    public Repository repository() {
        return new Repository();
    }
}
```

---

### 4. Jar 실행

```bash
java -jar app.jar
```

- 하나의 파일로 실행 가능

---

## 핵심 정리

- IoC: 객체 관리 주체가 개발자 → 스프링
- DI: 객체를 외부에서 주입
- AOP: 공통 로직 분리
- POJO: 순수 Java 객체
- Spring Boot: 자동 설정 + 내장 WAS로 개발 생산성 향상