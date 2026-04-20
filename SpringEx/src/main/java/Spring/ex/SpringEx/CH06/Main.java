package Spring.ex.SpringEx.CH06;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // 드라이버 연결
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 데이터베이스 연결 SQLException예외 발
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "1234");

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM member where id > ?");
        preparedStatement.setLong(1, 2L);
        ResultSet result = preparedStatement.executeQuery();

        while (result.next()) {
            Member member = new Member(
                    result.getLong("id"),
                    result.getString("name"),
                    result.getString("email"),
                    result.getInt("age"));
            System.out.println("회원: " + member);
        }


        // 데이터베이스 다 사용 후 종료 해줘야한다.
        connection.close();
    }
}
