package Ex6;

import java.text.DecimalFormat;

public class OrderProcessor {
    public void processOrder(Order order, SalesChannelFactory channelFactory) {
        System.out.println("\n--- Đang xử lý đơn hàng: " + order.getProduct().getName() + " ---");
        System.out.println("Kênh bán hàng: " + channelFactory.getChannelName());

        DiscountStrategy discount = channelFactory.createDiscountStrategy();
        PaymentMethod payment = channelFactory.createPaymentMethod();
        NotificationService notification = channelFactory.createNotificationService();

        double finalAmount = discount.applyDiscount(order.getTotalAmount());
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("  Số tiền cần thanh toán: " + df.format(finalAmount) + " VNĐ");

        payment.processPayment();
        notification.sendNotification();
        System.out.println("--- Hoàn tất ---\n");
    }
}
