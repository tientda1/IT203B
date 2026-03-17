package Ex6;

public class EmailNotification implements NotificationService {
    @Override
    public void sendNotification(String message) { System.out.println("Gửi email: " + message); }
}
