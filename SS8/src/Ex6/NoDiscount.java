package Ex6;

public class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        System.out.println("Không áp dụng giảm giá: 0");
        return 0;
    }
}
