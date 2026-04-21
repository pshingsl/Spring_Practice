# Spring Data JDBC

## 개요

Spring Data JDBC는 JDBC(Java Database Connectivity)기반으로 관계형 데이터베이스(RDB)에 쉽게 접근할 수 있도록 돕는 Spring Data 라이브러리이다.



Spring Data JDBC는 **엄밀히 말하면 전통적인 ORM이 아니라**, ORM의 일부 개념을 차용한 데이터 접근 기술이다. (JPA는 ORM 기술이 맞음)


JPA처럼 객체와 데이터베이스를 매핑해 주지만, 영속성 컨텍스트(Persistence Context)가 없고, 미니멀한 데이터 접근 방식이다.

---

## 특징


- 단순한 구조  
  JPA처럼 복잡한 설정이나 매핑 규칙이 필요 없다.


- 명시적인 쿼리 동작  
  영속성 컨텍스트가 없어 지연 로딩(Lazy Loading)이나 캐싱이 발생하지 않는다.


- 예측 가능한 SQL 실행  
  SQL이 언제 어떻게 실행되는지 명확하다.


- 명시적인 저장 시점  
  JPA처럼 트랜잭션 종료 시점이 아니라,  
  repository.save() 호출 시점에 바로 DB에 반영된다.

---

## 참고 개념

### ORM(Object-Relational Mapping)
객체와 관계형 데이터베이스를 매핑해주는 기술

### 영속성 컨텍스트(Persistence Context)

엔티티를 저장하고 관리하는 JPA의 핵심 기능이다.  
변경 감지(Dirty Checking), 캐싱, 지연 로딩 등을 제공한다.

### 지연 로딩(Lazy Loading)

실제 데이터가 필요한 시점에 조회하는 방식

### 캐싱(Cache)

이미 조회한 데이터를 메모리에 저장해 재사용하는 방식

---

## Spring JDBC 사용하는 이유

Spring JDBC를 사용하면 일반 JDBC에서 사용하는 반복적인 자원 관리 코드(Connection, Statement, close 등)를 줄일 수 있어
더 효율적이고 유지보수가 쉬운 코드를 작성할 수 있다.

---

## Spring Data JDBC 장점

### 1) 단순한 구조



Spring Data JDBC는 직관적인 CRUD 메서드와 명시적인 쿼리 실행을 제공하기 때문에 복잡한 매핑 설정 없이도 테이블 구조를 쉽게 다룰 수 있다.

SQL 실행 흐름이 명확하여 유지보수가 쉽고 예측 가능하다.

N+1 문제를 자동으로 해결해주지는 않지만, 지연 로딩이 없기 때문에 개발자가 쿼리를 직접 제어할 수 있다.

---

### 2) 가벼운 의존성

JPA는 Hibernate 같은 구현체가 필요하지만, Spring Data JDBC는 별도의 ORM 구현체 없이 JDBC 기반으로 동작한다.

프로젝트 규모가 작거나 단순한 구조에서 유리하다.

---

### 3) 명시적인 저장 시점

JPA에서는 트랜잭션 종료 시점에 자동 반영되지만, Spring Data JDBC는 save() 호출 시 즉시 SQL이 실행된다.

이는 영속성 컨텍스트가 없기 때문에 가능한 구조이다.

---

## 주의할 점 ⚠️

### 1) 복잡한 연관관계 비권장

Spring Data JDBC는 1:N, N:1 정도의 단순 관계에 적합하다.

다대다(M:N), 양방향 관계, 복잡한 객체 그래프는 JPA가 더 적합하다.

---

### 2) 대용량 데이터 처리


지연 로딩이 없기 때문에 필요한 데이터만 나눠서 조회하지 않으면
한 번에 많은 데이터를 조회하게 되어 성능 문제가 발생할 수 있다.


PagingAndSortingRepository를 사용하거나, 필요한 경우 직접 페이징 SQL을 작성해야 한다.

---

### 3) 변경 감지 기능 없음

JPA의 Dirty Checking 기능이 없어 엔티티 일부만 변경하더라도 save()를 호출해야 한다.


데이터 흐름이 명확하지만, 자동화된 편의 기능은 부족하다.
