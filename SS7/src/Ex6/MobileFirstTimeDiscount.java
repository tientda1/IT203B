package Ex6;

public class MobileFirstTimeDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        System.out.println("  Áp dụng giảm giá 15% cho lần đầu mua qua App");
        return amount * 0.85;
    }
}
