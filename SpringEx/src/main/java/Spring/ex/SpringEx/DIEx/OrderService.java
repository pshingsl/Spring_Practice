package Spring.ex.SpringEx.DIEx;

public class OrderService {
    private Notification notification;

    // 생성자로 의존주입 방법
    public OrderService(Notification notification) {
        this.notification = notification;
    }

    // 세터로 의존주입 방법
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public void order(){
        System.out.println("오더 입니다.");
        notification.send(); // 이게 없으면 각 클래스에서 정의한 메세드가 호출되지 않음
    }
}