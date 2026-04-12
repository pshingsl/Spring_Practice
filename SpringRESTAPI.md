# Spring REST API 정리

---

## REST (Representational State Transfer)

웹 서비스 개발에 널리 사용되는 아키텍처 스타일  
HTTP 프로토콜 기반으로 동작한다.

---

## REST 핵심 개념

### 1. 자원 (Resource)

- REST에서는 모든 것을 "자원"으로 본다.
- 자원은 URI(Uniform Resource Identifier)로 식별한다.

예:
```
/users
/users/1
/posts/10
```

---

### 2. 표현 (Representation)

- 클라이언트가 요청한 자원의 상태를 특정 형식으로 전달
- 주로 JSON, XML 사용

```json
{
  "id": 1,
  "name": "coco"
}
```

---

### 3. 상태 전달 (Stateless)

- 서버는 클라이언트의 상태를 저장하지 않는다.
- 각 요청은 독립적으로 처리된다.

예:
- 매 요청마다 인증 토큰 포함 필요 (JWT 등)

---

## REST API

REST 원칙을 따르는 API  
클라이언트와 서버 간의 통신을 REST 방식으로 설계한 것

---

## RESTful API 구성 요소

### 1. URI (자원 식별)

- URL을 통해 자원을 식별

```
GET /users
GET /users/1
```

---

### 2. HTTP Method (행위)

| Method | 설명 |
|--------|------|
| GET | 조회 |
| POST | 생성 |
| PUT | 전체 수정 |
| PATCH | 부분 수정 |
| DELETE | 삭제 |

---

### 3. HTTP 상태 코드

서버의 처리 결과를 숫자로 반환

| 코드 | 의미 |
|------|------|
| 200 | OK (성공) |
| 201 | Created (생성 성공) |
| 400 | Bad Request (잘못된 요청) |
| 401 | Unauthorized (인증 필요) |
| 403 | Forbidden (권한 없음) |
| 404 | Not Found (리소스 없음) |
| 500 | Internal Server Error (서버 오류) |

---

### 4. Header

요청/응답에 대한 메타데이터 포함

- Content-Type
- Authorization
- Cache-Control

---

### 5. Body

- 실제 데이터(JSON 등) 포함

---

## RESTful API 핵심 원칙

### 1. 클라이언트-서버 구조

- 역할 분리
- 클라이언트: 요청
- 서버: 처리 및 응답

---

### 2. 무상태성 (Stateless)

- 서버는 상태를 저장하지 않음
- 요청마다 필요한 정보 포함

---

### 3. 캐시 가능 (Cacheable)

- 응답을 캐싱하여 성능 향상 가능

---

### 4. 계층화 구조 (Layered System)

- 클라이언트는 중간 서버 존재 여부를 모름
- 예: API Gateway, Proxy, Load Balancer

---

## REST 설계 규칙

### 1. URI 규칙

- 마지막에 "/" 사용하지 않음
  ```
  /users (O)
  /users/ (X)
  ```

- 소문자 사용
  ```
  /users (O)
  /Users (X)
  ```

- 하이픈(-) 사용, 언더바(_) 지양
  ```
  /user-profile (O)
  /user_profile (X)
  ```

---

### 2. 명사 사용 (중요)

- URI는 "자원"을 표현해야 한다

```
GET /users       (O)
GET /getUsers    (X)
POST /createUser (X)
```

👉 행위는 HTTP Method로 표현

---

## 핵심 정리

- REST는 HTTP 기반 아키텍처 스타일
- 자원(URI) + 행위(Method)로 설계
- 상태는 저장하지 않음 (Stateless)
- JSON 형태로 데이터 전달
- URI는 명사, 행위는 HTTP Method

---

## 예시

### 회원 조회

```
GET /users/1
```

응답:

```json
{
  "id": 1,
  "name": "coco"
}
```

---

### 회원 생성

```
POST /users
```

요청:

```json
{
  "name": "coco"
}
```

응답:

- 201 Created