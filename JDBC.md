# JDBC

## JDBC란?

자바 프로그램에서 관계형 데이터베이스에 접속하여 데이터를 조회, 삽입, 수정, 삭제하는 작업을 수행하게 해주는 표준 API이다.  
다양한 DBMS를 동일한 코드 구조로 다룰 수 있게 하여 DB 변경 시 코드 수정을 최소화한다.

## JDBC의 핵심 인터페이스

JDBC는 3가지 주요 인터페이스를 제공한다.

- java.sql.Connection : 데이터베이스 연결
- java.sql.Statement : SQL 실행 객체
- java.sql.ResultSet : SQL 실행 결과 저장 객체



Spring Data JDBC, Spring Data JPA 등의 기술이 등장하면서 JDBC API를 직접 사용하는 경우는 줄어들었다.

## JDBC 동작 흐름

Java 애플리케이션 → JDBC API → JDBC Driver → Database

## JDBC API 처리 순서

JDBC 드라이버 로딩 → Connection 생성 → Statement 생성 → Query 실행 → ResultSet 조회  
→ ResultSet close → Statement close → Connection close

## JDBC Driver

JDBC Driver는 인터페이스가 아니라 구현체(클래스)이다.  
각 DBMS 벤더(MySQL, Oracle 등)가 JDBC 표준 인터페이스를 구현하여 제공한다.  
이 드라이버가 실제로 데이터베이스와 통신을 수행한다.

---
# JDBC 예제 코드

## 기본 CRUD 예제
```java
import java.sql.*;

public class JdbcExample {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "1234";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {

            // INSERT
            String insertSql = "INSERT INTO member(name, age) VALUES(?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, "kim");
                pstmt.setInt(2, 25);
                pstmt.executeUpdate();
            }

            // SELECT
            String selectSql = "SELECT * FROM member";
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");

                    System.out.println(id + " " + name + " " + age);
                }
            }

            // UPDATE
            String updateSql = "UPDATE member SET age=? WHERE name=?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateSql)) {
                pstmt.setInt(1, 30);
                pstmt.setString(2, "kim");
                pstmt.executeUpdate();
            }

            // DELETE
            String deleteSql = "DELETE FROM member WHERE name=?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteSql)) {
                pstmt.setString(1, "kim");
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```
---

## 장단점

### 장점
- 표준 API로 다양한 DB 연결 가능
- SQL을 직접 사용하여 세밀한 제어 가능
- 가볍고 추가 ORM 의존성이 없음

### 단점
- 반복 코드 발생 (Connection, Statement, ResultSet 처리)
- 자원 관리 코드 필요 (close 필수)
- 객체 매핑 수동 처리 (ResultSet → 객체)
- 트랜잭션 직접 관리 필요
- 유지보수 어려움 (SQL + Java 코드 강결합)
---

# 트랜잭션

## 트랜잭션이란?

데이터베이스에서 데이터 무결성을 보장하기 위한 작업 단위이다.  
여러 연산을 하나로 묶어 **모두 성공하거나 모두 실패**해야 한다.

---

## ACID 속성


- Atomicity (원자성) : 모두 성공 또는 모두 실패
- Consistency (일관성) : 트랜잭션 전후 데이터 일관성 유지
- Isolation (격리성) : 트랜잭션 간 간섭 없음
- Durability (지속성) : 완료된 결과는 영구 저장

---

## JDBC에서 트랜잭션 관리

### 기본 동작

JDBC의 Connection은 기본적으로 auto-commit = true 상태이다.

### 트랜잭션 처리 흐름


1. Auto-commit 비활성화
    - conn.setAutoCommit(false)

2. SQL 실행
    - INSERT / UPDATE / DELETE

3. 성공 시
    - conn.commit()

4. 실패 시
    - conn.rollback()

5. 자원 해제
    - Connection / Statement close

---

## JDBC 트랜잭션 정리

JDBC 트랜잭션은 여러 SQL 작업을 하나의 논리적 단위(ACID)로 묶어 데이터 무결성을 보장한다.  
기본적으로 auto-commit 모드로 동작하며, 수동 트랜잭션은 setAutoCommit(false)로 시작한다.  
작업 성공 시 commit(), 실패 시 rollback()을 호출한다.

---

## 코드 예시


```java
public class SimpleTransaction {

  public static void main(String[] args) {
    String url = "jdbc:mysql://localhost:3306/your_database";
    String user = "your_user";
    String password = "your_password";

    try (Connection conn = DriverManager.getConnection(url, user, password)) {

      conn.setAutoCommit(false); // 트랜잭션 시작

      try {
        insertData(conn, "New Data");
        updateData(conn, "Updated Data", 1);

        conn.commit(); // 성공 시 커밋

      } catch (SQLException e) {
        conn.rollback(); // 실패 시 롤백
        throw e;
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private static void insertData(Connection conn, String data) throws SQLException {
    String sql = "INSERT INTO your_table (column_name) VALUES (?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, data);
      pstmt.executeUpdate();
    }
  }

  private static void updateData(Connection conn, String data, int id) throws SQLException {
    String sql = "UPDATE your_table SET column_name = ? WHERE id = ?";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, data);
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
    }
  }
}