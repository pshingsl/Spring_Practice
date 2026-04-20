package Spring.ex.SpringEx.CH06;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 드라이버 연결
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 데이터베이스 연결 SQLException예외 발
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "1234");

        // 멤베 테이블 생성 및 값 등록
        Scanner sc = new Scanner(System.in);
        List<Member> members = new ArrayList<>();

        // 트랜잭션 적용
        connection.setAutoCommit(false);
        try {
            while (true) {
                System.out.println("사용자를 등록 해주세요.");
                String name = sc.next();
                if (name.equals("exit")) break;

                System.out.print("이메일: ");
                String email = sc.next();

                System.out.print("나이: ");
                int age = sc.nextInt();

                members.add(new Member(null, name, email, age));
            }

            PreparedStatement createStatement = connection.prepareStatement("insert into member(name, email, age) values(?,?,?)");
            for (Member m : members) {
                createStatement.setString(1, m.getName());
                createStatement.setString(2, m.getEmail());
                createStatement.setInt(3, m.getAge());
                createStatement.addBatch();
            }

            int[] create = createStatement.executeBatch();
            System.out.println("멤버 생성: " + Arrays.toString(create));

            // 조회
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member");
//        preparedStatement.setLong(1, 2L);
            ResultSet result = preparedStatement.executeQuery();

            while (result.next()) {
                Member member = new Member(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("email"),
                        result.getInt("age"));
                System.out.println("회원: " + member);
            }

            // 업데이트
            System.out.print("수정할 ID: ");
            Long id = sc.nextLong();

            System.out.println("수정할 이메일: ");
            String email = sc.next();

            System.out.println("수정할 나이: ");
            Integer age = sc.nextInt();

            System.out.println("수정할 이름: ");
            String name = sc.next();

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE member SET name = ?, email = ?, age = ?  WHERE id = ?");
            updateStatement.setString(1, name);
            updateStatement.setString(2, email);
            updateStatement.setInt(3, age);
            updateStatement.setLong(4, id);

            int updateResult = updateStatement.executeUpdate();
            System.out.println("수정 후:" + updateResult);

            // 삭제
            System.out.print("삭제할 ID: ");
            Long deleteId = sc.nextLong();

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM member WHERE id  = ? ");
            deleteStatement.setLong(1, deleteId);
            int deleteResult = deleteStatement.executeUpdate();
            System.out.println("삭제 후: " + deleteResult);

            // 트랜잭션 커밋(모든작업 성공 시 출력)
            connection.commit();
            System.out.println("트랜잭션 커밋 완료");
        } catch(Exception e){
            // 예외가 발생 시 기존걸로 수정해라 -> 하나라도 실패 시 rollback
            connection.rollback();
            System.out.println("트랜잭션 롤백");
            e.printStackTrace();
        } finally {
            // 데이터베이스 다 사용 후 종료 해줘야한다.
             connection.close();
             sc.close();
        }
    }
}
