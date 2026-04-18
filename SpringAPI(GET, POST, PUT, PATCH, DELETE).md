# Spring API Annotation (GET, POST, PUT, PATCH, DELETE)

---

## GET
REST API에서 서버의 리소스를 조회(Read)하기 위해 사용하는 HTTP 메소드이다.

URL(경로)에 데이터를 포함하여 요청하며, 동일한 요청을 여러 번 보내도 결과가 변하지 않는 **멱등성**을 가진다.

스프링부트에서는 컨트롤러에서 `@GetMapping` 사용

```java
@GetMapping("/users")
public String getUsers() {
    return "users";
}
```

---

## Query String
URL을 통해 데이터를 전달하는 방식

주로 검색, 페이지, 필터 등에 사용

```
/users?name=kim&age=20
```

### 예시코드
```java
@GetMapping("/users")
public String getUser(
    @RequestParam(name = "name", required = false) String name
) {
    return name;
}
```

---

## PathVariable
URL 경로의 일부를 변수로 사용하는 방식

```java
@GetMapping("/users/{id}")
public String getUserById(@PathVariable Long id) {
    return "user id = " + id;
}
```

---

## POST
클라이언트가 서버로 데이터를 전송하여 리소스를 생성(Create)하거나 상태를 변경할 때 사용

- 데이터는 HTTP Body에 포함
- URL에 노출되지 않음

```java
@PostMapping("/users")
public String createUser(@RequestBody User user) {
    return "created";
}
```

---

## @ResponseBody
컨트롤러 메소드가 View를 거치지 않고 데이터를 HTTP 응답 Body로 직접 반환

→ JSON/XML 형태로 변환됨

```java
@ResponseBody
@GetMapping("/api/user")
public User apiUser() {
    return new User("kim", 20);
}
```

---

## PUT
리소스를 **전체 수정(Replace)** 하거나 생성할 때 사용

- 멱등성 있음
- 클라이언트가 리소스 ID를 알고 있어야 함

```java
@PutMapping("/users/{id}")
public User updateUser(
    @PathVariable Long id,
    @RequestBody User user
) {
    return user;
}
```

---

## PATCH
리소스를 **부분 수정(Partial Update)** 할 때 사용

- 일부 필드만 변경
- 멱등성 보장되지 않을 수 있음

```java
@PatchMapping("/users/{id}")
public User patchUser(
    @PathVariable Long id,
    @RequestBody User user
) {
    return user;
}
```

---

## DELETE
리소스를 삭제(Delete)할 때 사용하는 HTTP 메소드

주로 @PathVariable을 통해 식별자를 받아 특정 데이터를 삭제한다.


```java
@DeleteMapping("/users/{id}")
public String deleteUser(@PathVariable Long id) {
    return "deleted";
}
```