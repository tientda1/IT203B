package Ex6;

import java.text.DecimalFormat;
public class CODPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán COD (Tiền mặt): " + new DecimalFormat("#,###").format(amount));
    }
}
