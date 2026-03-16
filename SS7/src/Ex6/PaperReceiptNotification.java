package Ex6;

public class PaperReceiptNotification implements NotificationService {
    @Override
    public void sendNotification() {
        System.out.println("  In hóa đơn giấy tại quầy");
    }
}
