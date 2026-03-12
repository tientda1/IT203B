package BTTH;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductManager manager = new ProductManager();
        int choice = -1;

        while (choice != 5) {
            System.out.println("\n========= PRODUCT MANAGEMENT SYSTEM =========");
            System.out.println("1. Thêm sản phẩm mới");
            System.out.println("2. Hiển thị danh sách sản phẩm");
            System.out.println("3. Cập nhật số lượng theo ID");
            System.out.println("4. Xóa sản phẩm đã hết hàng");
            System.out.println("5. Thoát chương trình");
            System.out.println("=============================================");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên từ 1-5");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Nhập ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nhập tên sản phẩm: ");
                        String name = scanner.nextLine();
                        System.out.print("Nhập giá: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nhập số lượng: ");
                        int qty = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nhập danh mục: ");
                        String category = scanner.nextLine();

                        Product newProduct = new Product(id, name, price, qty, category);
                        manager.addProduct(newProduct);
                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Nhập sai định dạng số!");
                    }
                    break;
                case 2:
                    manager.displayProducts();
                    break;
                case 3:
                    try {
                        System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        System.out.print("Nhập số lượng mới: ");
                        int newQty = Integer.parseInt(scanner.nextLine());

                        manager.updateQuantity(updateId, newQty);
                    } catch (InvalidProductException e) {
                        System.out.println(e.getMessage());
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Nhập sai định dạng số!");
                    }
                    break;
                case 4:
                    manager.deleteOutOfStock();
                    break;
                case 5:
                    System.out.println("Đang thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }
}
