package Ex6;

public class PushNotification implements NotificationService {
    @Override
    public void sendNotification(String message) { System.out.println("Gửi push notification: " + message); }
}
