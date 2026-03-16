package Ex6;

public class StorePOSFactory implements SalesChannelFactory {
    @Override public DiscountStrategy createDiscountStrategy() { return new POSMemberDiscount(); }
    @Override public PaymentMethod createPaymentMethod() { return new TerminalPayment(); }
    @Override public NotificationService createNotificationService() { return new PaperReceiptNotification(); }
    @Override public String getChannelName() { return "POS Cửa hàng"; }
}
