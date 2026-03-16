package Ex3;

import java.text.DecimalFormat;

interface PaymentMethod {
    void processPayment(double amount);
}

interface CODPayable extends PaymentMethod {}
interface CardPayable extends PaymentMethod {}
interface EWalletPayable extends PaymentMethod {}

class CODPayment implements CODPayable {
    @Override
    public void processPayment(double amount) {
        System.out.println("Xử lý thanh toán COD: " + formatCurrency(amount) + " - Thành công");
    }

    private String formatCurrency(double amount) {
        return new DecimalFormat("#,###").format(amount).replace(',', '.');
    }
}

class CreditCardPayment implements CardPayable {
    @Override
    public void processPayment(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + formatCurrency(amount) + " - Thành công");
    }

    private String formatCurrency(double amount) {
        return new DecimalFormat("#,###").format(amount).replace(',', '.');
    }
}

class MomoPayment implements EWalletPayable {
    @Override
    public void processPayment(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + formatCurrency(amount) + " - Thành công");
    }

    private String formatCurrency(double amount) {
        return new DecimalFormat("#,###").format(amount).replace(',', '.');
    }
}


class PaymentProcessor {
    public void process(PaymentMethod paymentMethod, double amount) {
        paymentMethod.processPayment(amount);
    }
}

public class Main {
    public static void main(String[] args) {
        PaymentProcessor processor = new PaymentProcessor();

        PaymentMethod codPayment = new CODPayment();
        PaymentMethod cardPayment = new CreditCardPayment();
        PaymentMethod momoPayment = new MomoPayment();

        System.out.println("--- Giao dịch thông thường ---");
        processor.process(codPayment, 500000);
        processor.process(cardPayment, 1000000);
        processor.process(momoPayment, 750000);

        System.out.println("\n--- Kiểm tra LSP ---");
        System.out.println("Khách hàng ban đầu định dùng Thẻ tín dụng...");
        PaymentMethod currentMethod = new CreditCardPayment();

        System.out.println("Nhưng sau đó đổi ý, chuyển sang dùng MoMo...");
        currentMethod = new MomoPayment();

        processor.process(currentMethod, 1000000);
    }
}
