#  Spring JPA 정리

---

# 개념

 
Spring JPA는 자바 ORM 기술 표준인 JPA(Java Persistence API)를  
스프링에서 더 쉽게 사용할 수 있도록 도와주는 기술이다.


정확히 말하면 Spring Data JPA는 JPA를 편하게 쓰도록 도와주는 추상화 라이브러리이다.


"DB를 직접 SQL로 다루지 않고, 객체로 쉽게 다루게 해주는 기술"*

---

#  핵심 특징

- 인터페이스만 선언하면 CRUD 자동 생성
- SQL 중심 개발 → 객체 중심 개발로 변경
 
"코드 몇 줄로 DB 작업 끝낼 수 있음"

---

#  JPA 장점


- 생산성이 높음 (SQL 작성 줄어듦)
- 유지보수 쉬움
- DB 변경에 유연함

 
MySQL → PostgreSQL 변경 시 코드 수정 최소화

---

#  JPA 단점


- 학습 난이도가 높음 (개념 많음)
- 복잡한 쿼리 작성이 어려움
- 잘못 사용하면 성능 문제 발생 가능

"편하지만 제대로 알아야 함"

---

# 구현체 (Hibernate)



Hibernate는 JPA의 대표적인 구현체이다.

 구조
```
JPA (인터페이스)
   ↓
Hibernate (구현체)
   ↓
JDBC (DB 연결)
```



- 객체 ↔ 테이블 자동 매핑
- SQL 없이 CRUD 가능
- 내부적으로 JDBC 사용

---

# Hibernate 장점

- SQL 없이 메서드 호출로 DB 작업 가능
- 반복 SQL 제거 → 생산성 향상
- 테이블 구조 변경 시 유지보수 편함
- DB 변경 유연함

---

#  Hibernate 단점

[확실]

- 직접 SQL보다 성능이 떨어질 수 있음
- 복잡한 쿼리 표현 한계 존재

이를 보완하기 위해 JPQL을 사용한다.
 
Native Query를 사용하면 직접 SQL 작성도 가능하다.

---

#  Entity

Entity는 데이터베이스 테이블과 매핑되는 Java 클래스이다.
 
"DB 테이블을 코드로 만든 것"

- 테이블 한 개 ↔ Entity 한 개
- 필드 ↔ 컬럼

---

#  Entity 특징


- 기본 생성자 필요
- @Id 필수 (PK)
- 영속성 컨텍스트에서 관리됨

---

#  Entity 관련 어노테이션

---

## @Entity

  
이 클래스가 Entity임을 선언

---

## @Table

매핑할 테이블 이름 지정

---

## @Id

Primary Key 지정

---

## @GeneratedValue

 
PK 생성 전략 지정

### 전략 종류

- IDENTITY  

  DB에 위임 (AUTO_INCREMENT)  


- AUTO  
  
  DB에 맞게 자동 선택

---

## @Column


필드와 컬럼 매핑

---

## @PrePersist

Entity가 저장되기 전에 실행되는 메서드

 예시  
createdAt 자동 설정

---

# ⚠️ 삭제 전략

  
실무에서는 물리 삭제보다 논리 삭제(soft delete)를 많이 사용한다.

 이유  
데이터 복구 및 감사 로그 때문
 
"진짜 삭제 안 하고, 삭제된 것처럼 표시만 함"

---

#  핵심 흐름

```
Controller → Service → Repository → JPA → Hibernate → DB
```
 
"요청 → 처리 → DB 저장"

---

#  한줄 정리


Spring JPA는  
"객체로 DB를 쉽게 다루게 해주는 기술"
