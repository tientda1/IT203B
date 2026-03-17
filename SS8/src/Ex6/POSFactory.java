package Ex6;

public class POSFactory implements SalesChannelFactory {
    public DiscountStrategy createDiscountStrategy() { return new MemberDiscount(); }
    public PaymentMethod createPaymentMethod() { return new CODPayment(); }
    public NotificationService createNotificationService() { return new PrintReceipt(); }
}
