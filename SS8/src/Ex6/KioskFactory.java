package Ex6;

public class KioskFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new NoDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CreditCardPayment(); }
    public NotificationService createNotificationService() { return new PrintReceipt(); }
}
