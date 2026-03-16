package Ex6;

public class OnlineCardPayment implements PaymentMethod {
    @Override
    public void processPayment() {
        System.out.println("  Xử lý thanh toán thẻ tín dụng qua cổng thanh toán online");
    }
}
