package Ex6;

import java.text.DecimalFormat;
public class FirstTimeDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        double discount = amount * 0.15;
        System.out.println("Áp dụng giảm giá 15% (lần đầu): " + new DecimalFormat("#,###").format(discount));
        return discount;
    }
}
