package Ex6;

public class PushNotification implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("  Gửi push notification: Đơn hàng thành công");
    }
}
