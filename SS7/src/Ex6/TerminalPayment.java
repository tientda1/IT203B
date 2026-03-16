package Ex6;

public class TerminalPayment implements PaymentMethod {
    @Override
    public void processPayment() {
        System.out.println("  Xử lý quẹt thẻ qua máy POS vật lý");
    }
}
