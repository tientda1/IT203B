package Ex6;

public interface SalesChannelFactory {
    DiscountStrategy createDiscountStrategy();
    PaymentMethod createPaymentMethod();
    NotificationService createNotificationService();
}
