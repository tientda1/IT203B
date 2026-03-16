package Ex6;

public class MomoPayment implements PaymentMethod {
    @Override
    public void processPayment() {
        System.out.println("  Xử lý thanh toán MoMo tích hợp");
    }
}
