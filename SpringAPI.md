# Spring API 정리

---

## Spring API
Spring API는 스프링(Spring) 프레임워크를 기반으로 구축된, 시스템 간 데이터를 교환하기 위한 규칙 및 인터페이스다.

주로 RESTful 형식을 통해  
클라이언트(앱, 웹)와 서버가 JSON/XML 데이터로 통신하며,  
HTTP 메서드를 활용해 자원을 관리하는 경량 인터페이스이다.

---

## MIME Type (Multipurpose Internet Mail Extensions)
인터넷에서 파일의 형식을 나타내는 표준이다.

HTTP 통신 시 데이터의 형식(문서, 이미지 등)을 식별하는 라벨이며,주로 **Content-Type 헤더**를 통해 정의된다.

이메일 첨부파일 형식을 위해 처음 개발되었으나, 현재는 웹에서 광범위하게 사용된다.

스프링 부트에서는 `MediaType`을 사용하여  
다양한 타입을 간편하게 설정할 수 있다.

- application/json
- text/html
- image/jpeg

또한 파일 업로드/다운로드 시 적절한 컨텐츠 타입을 지정할 수 있다.

---

## HTTP Content-Type
클라이언트와 서버 간 데이터를 주고 받을 때, 요청(Request) 또는 응답(Response)의

**본문(Body)에 담긴 데이터 형식(MIME 타입)을 명시하는 HTTP 헤더**이다.

서버는 이 값을 통해 JSON, XML 등의 처리 방식을 결정한다.

### 주요 타입
- text/plain : 일반 텍스트
- text/html : HTML 문서
- application/json : JSON 데이터

요청 바디에 들어가는 타입을  
요청 헤더의 `Content-Type`에서 명시한다.

### 예시 (HTTP 요청)
```
POST /api/users HTTP/1.1
Content-Type: application/json

{
  "name": "kim",
  "age": 20
}
```

---

## 폼 데이터 (Form Data)

### application/x-www-form-urlencoded
기본적인 HTML 폼 전송 방식

키-값 쌍 데이터를 URL 인코딩 방식으로 변환하여 전송한다.

```
key=value
key1=value1&key2=value2
```

- 공백 → `+`
- 특수문자 → `%xx`

### 예시
```
POST /login
Content-Type: application/x-www-form-urlencoded

username=kim&password=1234
```

---

### multipart/form-data
파일(이미지, PDF 등)과 텍스트 데이터를  
**동시에 전송할 때 사용하는 방식**이다.

파일 업로드가 포함된 경우 사용된다.

### 예시
```
POST /upload
Content-Type: multipart/form-data

file: image.png
description: 프로필 이미지
```

---

## JSON 및 XML

### application/json
데이터 형식이 JSON(JavaScript Object Notation)임을 나타내는 MIME 타입이다.

스프링부트에서는  
`@RequestBody` + Jackson 라이브러리를 통해

- JSON → Java 객체
- Java 객체 → JSON

자동 변환하여 REST API를 구현한다.

프론트엔드에서는 axios, Fetch API 등을 통해 비동기적으로 데이터를 전송할 때 주로 사용된다.

### 예시 (Controller)
```java
@PostMapping("/users")
public User createUser(@RequestBody User user) {
    return user;
}
```

---

### application/xml
XML 형식의 데이터를 나타내는 MIME 타입이다.

### 예시
```xml
<user>
  <name>kim</name>
  <age>20</age>
</user>
```

---

## API (GET, POST)

### GET
서버의 리소스를 조회(Read)할 때 사용하는 HTTP 메소드

- URL에 데이터 포함
- 멱등성(Idempotent) 보장

**멱등성**  
→ 동일한 요청을 여러 번 보내도 서버 상태가 변하지 않는 성질

```java
@GetMapping(url주소)
 접근제한자 리턴타입 함수이름() {
	return 템플릿 파일명;
}
```


```java
@GetMapping("/users")
public List<User> getUsers() {
    return List.of(new User("kim", 20));
}
```

---

### Query String
URL을 통해 데이터를 전달하는 방식

URL 주소 미리 협의된 데이터를 파라미터를 통해 넘기는것을 말한다.

```
URL주소?key=value  or URL주소?key1=value1&key2=value2
```


```
/users?name=kim&age=20
```

주로 검색, 페이지, 필터 등에 사용된다.


```java
@GetMapping(url주소)
public 리턴타입 함수이름(@RequestParam(파라미터명, [required=true or false]) 타입 변수) {
        return 템플릿 파일명;
}
```

```java
@GetMapping("/users")
public String getUser(
    @RequestParam(name = "name", required = false) String name
) {
    return name;
}
```

---

### PathVariable
URL 경로 자체를 변수로 사용하는 방식


```java
@GetMapping(url주소/{value})
접근제한자 리턴타입 메소드명(@PathVariable 타입 변수명) {
	return 템플릿 파일명;
}
```


```java
@GetMapping("/users/{id}")
public String getUserById(@PathVariable Long id) {
    return "user id = " + id;
}
```

---

### POST
데이터를 서버로 전송하여 **리소스를 생성(Create)하거나 상태를 변경**할 때 사용

- 데이터는 HTTP Body에 포함
- URL에 노출되지 않음

```java
@PostMapping(url주소)
접근제한자 리턴타입 메소드명() {
	return 템플릿 파일명;
}
``` 


```java
@PostMapping("/users")
public String createUser() {
    return "created";
}
```

---

## @ResponseBody
컨트롤러에서 View를 거치지 않고 **데이터를 직접 HTTP 응답 Body로 반환**할 때 사용

주로 JSON/XML 형태로 반환되며, 객체는 `HttpMessageConverter`를 통해 변환된다.

```java
어노테이션
@ResponsBody
접근제한자 리턴타입 메소드명() {
	return 템플릿 파일명;
}
```


```java
@ResponseBody
@GetMapping("/api/user")
public User apiUser() {
    return new User("kim", 20);
}
```