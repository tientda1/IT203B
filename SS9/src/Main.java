import entity.Product;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDatabase db = ProductDatabase.getInstance();

        while (true) {
            System.out.println("\n---------------------- QUẢN LÝ SẢN PHẨM ----------------------");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Xem danh sách sản phẩm");
            System.out.println("3. Cập nhật thông tin sản phẩm");
            System.out.println("4. Xoá sản phẩm");
            System.out.println("5. Thoát");
            System.out.println("--------------------------------------------------------------------");
            System.out.print("Lựa chọn của bạn: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Chọn loại sản phẩm (1 - Physical, 2 - Digital): ");
                    int type = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập Tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập Giá: ");
                    double price = scanner.nextDouble();

                    double extraAttr = 0;
                    if (type == 1) {
                        System.out.print("Nhập Trọng lượng (kg): ");
                        extraAttr = scanner.nextDouble();
                    } else if (type == 2) {
                        System.out.print("Nhập Dung lượng (MB): ");
                        extraAttr = scanner.nextDouble();
                    } else {
                        System.out.println("Loại không hợp lệ!");
                        break;
                    }

                    try {
                        Product newProduct = ProductFactory.createProduct(type, id, name, price, extraAttr);
                        db.addProduct(newProduct);
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- DANH SÁCH SẢN PHẨM ---");
                    List<Product> list = db.getAllProducts();
                    if (list.isEmpty()) {
                        System.out.println("Kho hàng trống.");
                    } else {
                        for (Product p : list) {
                            p.displayInfo();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    System.out.print("Nhập Tên mới: ");
                    String newName = scanner.nextLine();
                    System.out.print("Nhập Giá mới: ");
                    double newPrice = scanner.nextDouble();
                    db.updateProduct(updateId, newName, newPrice);
                    break;

                case 4:
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String deleteId = scanner.nextLine();
                    db.deleteProduct(deleteId);
                    break;

                case 5:
                    System.out.println("Đã thoát chương trình.");
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        }
    }
}
