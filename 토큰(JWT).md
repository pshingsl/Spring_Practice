ㄱ# JWT (JSON Web Token) 정리

## 1. JWT 개념

*  JWT는 클라이언트와 서버 간 **인증/인가를 위해 사용하는 토큰 기반 방식**
*  JSON 형태의 데이터를 Base64로 인코딩하여 전달하는 토큰


**“서버 상태를 저장하지 않고 인증 정보를 전달하는 토큰”**

---

## 2. JWT 특징

*  서버가 세션을 저장하지 않는 **Stateless 구조**
*  토큰 자체에 사용자 정보 포함
*  HTTP Header에 담아서 요청

```text id="jwt-format"
Authorization: Bearer {JWT_TOKEN}
```

---

## 3. JWT 구조

JWT는 3가지로 구성된다.

```text id="jwt-structure"
Header.Payload.Signature
```

---

### 3.1 Header

*  토큰 타입과 암호화 알고리즘 정보 포함

예시:

```json id="jwt-header"
{
  "alg": "HS256",
  "typ": "JWT"
}
```

---

### 3.2 Payload

* 사용자 정보(Claim) 저장 영역
*  Base64 인코딩되어 있어 **복호화 가능 (암호화 아님)**

👉 주요 Claim

```json id="jwt-payload"
{
  "sub": "userId",
  "role": "USER",
  "iat": 1710000000,
  "exp": 1710003600
}
```

설명:

* sub: 사용자 식별자
* role: 권한
* iat: 발급 시간
* exp: 만료 시간

⚠️ 주의

*  Payload는 누구나 디코딩 가능
*  민감 정보 저장 금지
* 최소한의 정보(아이디, 비밀번호 등 개인정보가 아닌 이 토큰을 가졌을때 권한의 범위나 토큰의 발급일과 만료일자) 등만 담아야 한다.

---

### 3.3 Signature
가장 중요한 부분으로 헤더와 정보를 합친 후 발급해준 서버가 지정한 secret key로 암호화 시켜 토큰을 변조하기 어렵게 만들어 준다.


*  토큰 위변조 방지 역할
*  서버의 secret key로 생성

구조:

```text id="jwt-signature"
HMACSHA256(
  base64UrlEncode(header) + "." + base64UrlEncode(payload),
  secretKey
)
```

 핵심

*  Payload를 수정하면 Signature 불일치 발생 → 인증 실패

---

## 4. JWT 동작 방식

```text id="jwt-flow"
1. 사용자 로그인 요청
2. 서버 → JWT 발급
3. 클라이언트 저장 (LocalStorage / Cookie)
4. 요청 시 Header에 JWT 포함
5. 서버 → 토큰 검증 후 응답
```

*  서버는 세션을 저장하지 않음 (Stateless)

---

## 5. Spring Boot JWT 예시

### 5.1 JWT 생성

```java id="jwt-create"
public String createToken(String userId) {
    return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS256, "secretKey")
            .compact();
}
```

---

### 5.2 JWT 검증

```java id="jwt-validate"
public Claims validateToken(String token) {
    return Jwts.parser()
            .setSigningKey("secretKey")
            .parseClaimsJws(token)
            .getBody();
}
```

*  Signature 검증을 통해 위변조 여부 판단

---

## 6. 장점 / 단점

### 장점

*  서버 확장성에 유리 (Stateless)
*  서버 메모리 사용 감소 (세션 없음)
*  마이크로서비스 구조에 적합

---

### 단점

*  토큰 탈취 시 위험 (서버에서 즉시 무효화 어려움)
*  Payload 노출 가능
*  토큰 크기가 커질 수 있음

---

## 7. 보안 고려 사항

### 1. 민감 정보 금지

*  비밀번호, 개인정보 저장 금지

---

### 2. 만료 시간 설정

*  짧은 Access Token 권장

---

### 3. Refresh Token 사용

*  재발급 구조 필요

---

### 4. HTTPS 사용

*  네트워크 탈취 방지

---

### 5. 저장 위치

| 저장 위치        | 특징      |
| ------------ | ------- |
| LocalStorage | XSS 취약  |
| Cookie       | CSRF 취약 |

*  완벽한 방법은 없음 → 상황에 맞게 선택

---

## 8. 핵심 정리

*  JWT는 “인증 정보를 담은 토큰”
*  서버 상태를 저장하지 않는 인증 방식
*  Payload는 암호화가 아닌 인코딩
*  Signature로 위변조 검증

출처
https://velog.io/@hahan/JWT%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80

---

## 9. 면접 핵심 질문

1. JWT에서 Payload는 왜 위험한가?
2. JWT와 세션 방식 차이?
3. Access Token / Refresh Token 구조 설명?
4. JWT 탈취 시 대응 방법?
5. 왜 Stateless 구조가 중요한가?
