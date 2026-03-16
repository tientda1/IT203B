package Ex6;

public class MobileAppFactory implements SalesChannelFactory {
    @Override public DiscountStrategy createDiscountStrategy() { return new MobileFirstTimeDiscount(); }
    @Override public PaymentMethod createPaymentMethod() { return new MomoPayment(); }
    @Override public NotificationService createNotificationService() { return new PushNotification(); }
    @Override public String getChannelName() { return "Mobile App"; }
}
