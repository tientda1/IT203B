package Ex6;

import java.text.DecimalFormat;
public class MomoPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + new DecimalFormat("#,###").format(amount));
    }
}
