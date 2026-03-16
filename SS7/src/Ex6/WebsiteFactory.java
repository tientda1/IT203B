package Ex6;

public class WebsiteFactory implements SalesChannelFactory {
    @Override public DiscountStrategy createDiscountStrategy() { return new WebDiscount(); }
    @Override public PaymentMethod createPaymentMethod() { return new OnlineCardPayment(); }
    @Override public NotificationService createNotificationService() { return new EmailNotification(); }
    @Override public String getChannelName() { return "Website"; }
}
