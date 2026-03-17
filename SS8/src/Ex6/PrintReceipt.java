package Ex6;

public class PrintReceipt implements NotificationService {
    @Override
    public void sendNotification(String message) { System.out.println("In biên lai: " + message); }
}
