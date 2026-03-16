package Ex6;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderProcessor processor = new OrderProcessor();
        Order order = new Order("ORD-100", new Product("Laptop Gaming", 20000000));

        while (true) {
            System.out.println("=== CHỌN KÊNH BÁN HÀNG ===");
            System.out.println("1. Website");
            System.out.println("2. Mobile App");
            System.out.println("3. POS (Tại quầy)");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            String choice = scanner.nextLine();

            SalesChannelFactory selectedFactory = null;

            switch (choice) {
                case "1":
                    selectedFactory = new WebsiteFactory();
                    System.out.println("Bạn đã chọn kênh Website");
                    break;
                case "2":
                    selectedFactory = new MobileAppFactory();
                    System.out.println("Bạn đã chọn kênh Mobile App");
                    break;
                case "3":
                    selectedFactory = new StorePOSFactory();
                    System.out.println("Bạn đã chọn kênh POS");
                    break;
                case "0":
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    continue;
            }

            processor.processOrder(order, selectedFactory);
        }
    }
}
