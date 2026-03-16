package Ex6;

public class POSMemberDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double amount) {
        System.out.println("  Áp dụng giảm giá 5% cho thẻ thành viên tại quầy");
        return amount * 0.95;
    }
}
