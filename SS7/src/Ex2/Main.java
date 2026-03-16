package Ex2;

import java.text.DecimalFormat;

interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

class PercentageDiscount implements DiscountStrategy {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * percentage / 100);
    }
}

class FixedDiscount implements DiscountStrategy {
    private double discountAmount;

    public FixedDiscount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - discountAmount;
    }
}

class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

class OrderCalculator {
    private DiscountStrategy discountStrategy;

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public double calculateTotal(double totalAmount) {
        if (discountStrategy == null) {
            return totalAmount;
        }
        return discountStrategy.applyDiscount(totalAmount);
    }
}

class HolidayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount - (totalAmount * 15 / 100);
    }
}

public class Main {
    public static void main(String[] args) {
        double totalAmount = 1000000;
        OrderCalculator calculator = new OrderCalculator();

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng PercentageDiscount 10%");
        calculator.setDiscountStrategy(new PercentageDiscount(10));
        printResult(calculator.calculateTotal(totalAmount));

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng FixedDiscount 50.000");
        calculator.setDiscountStrategy(new FixedDiscount(50000));
        printResult(calculator.calculateTotal(totalAmount));

        System.out.println("Đơn hàng: tổng tiền 1.000.000, áp dụng NoDiscount");
        calculator.setDiscountStrategy(new NoDiscount());
        printResult(calculator.calculateTotal(totalAmount));

        System.out.println("Thêm HolidayDiscount 15% (không sửa code cũ)");
        calculator.setDiscountStrategy(new HolidayDiscount());
        printResult(calculator.calculateTotal(totalAmount));
    }

    private static void printResult(double amount) {
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedAmount = df.format(amount).replace(',', '.');
        System.out.println("Số tiền sau giảm: " + formattedAmount + "\n");
    }
}
