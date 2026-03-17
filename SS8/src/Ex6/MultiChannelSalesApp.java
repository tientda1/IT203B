package Ex6;

import java.util.Scanner;

public class MultiChannelSalesApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== HỆ THỐNG BÁN HÀNG ĐA KÊNH ===");
            System.out.println("1. Chọn kênh Website");
            System.out.println("2. Chọn kênh Mobile App");
            System.out.println("3. Chọn kênh POS");
            System.out.println("4. Chọn kênh Kiosk (Mới thêm)");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Đã thoát chương trình.");
                break;
            }

            SalesChannelFactory factory = null;

            switch (choice) {
                case 1:
                    System.out.println("Bạn đã chọn kênh Website");
                    factory = new WebsiteFactory();
                    break;
                case 2:
                    System.out.println("Bạn đã chọn kênh Mobile App");
                    factory = new MobileAppFactory();
                    break;
                case 3:
                    System.out.println("Bạn đã chọn kênh POS");
                    factory = new POSFactory();
                    break;
                case 4:
                    System.out.println("Bạn đã chọn kênh Kiosk");
                    factory = new KioskFactory();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    continue;
            }

            OrderService orderService = new OrderService(factory);

            System.out.print("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();

            System.out.print("Nhập giá (VNĐ): ");
            double price = scanner.nextDouble();

            System.out.print("Nhập số lượng: ");
            int quantity = scanner.nextInt();

            orderService.processOrder(productName, price, quantity);
        }
        scanner.close();
    }
}
