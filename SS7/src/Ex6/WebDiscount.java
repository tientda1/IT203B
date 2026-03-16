package Ex6;

public class WebDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        System.out.println("  Áp dụng giảm giá 10% (Mã: WEB10) cho đơn hàng website");
        return amount * 0.9;
    }
}
