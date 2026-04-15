package Spring.ex.SpringEx.DIEx;

public class SMSService implements Notification{
    @Override
    public void send() {
        System.out.println("SMS입니다.");
    }
}
