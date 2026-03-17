package Ex6;

public class MobileAppFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new FirstTimeDiscount(); }
    public PaymentMethod createPaymentMethod() { return new MomoPayment(); }
    public NotificationService createNotificationService() { return new PushNotification(); }
}
