package Ex6;

import java.text.DecimalFormat;

public class OrderService {
    private DiscountStrategy discountStrategy;
    private PaymentMethod paymentMethod;
    private NotificationService notificationService;

    // Dependency Injection qua Constructor
    public OrderService(SalesChannelFactory factory) {
        this.discountStrategy = factory.createDiscountStrategy();
        this.paymentMethod = factory.createPaymentMethod();
        this.notificationService = factory.createNotificationService();
    }

    public void processOrder(String productName, double price, int quantity) {
        System.out.println("\nTạo đơn hàng");
        System.out.println("Sản phẩm: " + productName + " giá " + new DecimalFormat("#,###").format(price) + ", số lượng " + quantity);

        double totalAmount = price * quantity;
        double discount = discountStrategy.applyDiscount(totalAmount);
        double finalAmount = totalAmount - discount;

        paymentMethod.pay(finalAmount);
        notificationService.sendNotification("Đơn hàng thành công");
    }
}
