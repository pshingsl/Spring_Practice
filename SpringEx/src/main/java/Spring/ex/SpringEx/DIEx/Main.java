package Spring.ex.SpringEx.DIEx;

public class Main {
    public static void main(String[] args) {
        Notification emailService = new EmailService();
        OrderService email = new OrderService(emailService);
        email.order();
        System.out.println("========");

        Notification SMSService = new SMSService();
        OrderService SMS = new OrderService(SMSService);
        SMS.order();
    }
}
