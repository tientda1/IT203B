package Ex6;

import java.text.DecimalFormat;
public class MemberDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        double discount = amount * 0.05;
        System.out.println("Áp dụng giảm giá 5% (thành viên): " + new DecimalFormat("#,###").format(discount));
        return discount;
    }
}
