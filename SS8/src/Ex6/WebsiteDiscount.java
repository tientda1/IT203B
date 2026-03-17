package Ex6;

import java.text.DecimalFormat;
public class WebsiteDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        double discount = amount * 0.10;
        System.out.println("Áp dụng giảm giá 10%: " + new DecimalFormat("#,###").format(discount));
        return discount;
    }
}
