package Ex6;

public class WebsiteFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new WebsiteDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CreditCardPayment(); }
    public NotificationService createNotificationService() { return new EmailNotification(); }
}
