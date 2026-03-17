package Ex6;

import java.text.DecimalFormat;
public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + new DecimalFormat("#,###").format(amount));
    }
}
