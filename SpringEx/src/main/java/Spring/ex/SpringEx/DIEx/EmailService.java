package Spring.ex.SpringEx.DIEx;

public class EmailService implements Notification{
    @Override
    public void send() {
        System.out.println("이메일 입니다.");
    }
}
