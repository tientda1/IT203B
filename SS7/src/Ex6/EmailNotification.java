package Ex6;

public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("  Gửi email xác nhận");
    }
}
