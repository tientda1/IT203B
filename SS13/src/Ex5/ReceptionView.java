package Ex5;

import java.util.List;
import java.util.Scanner;

public class ReceptionView {
    private AdmissionController controller = new AdmissionController();
    private Scanner scanner = new Scanner(System.in);

    public void startMenu() {
        while (true) {
            System.out.println("\n=== HỆ THỐNG TIẾP NHẬN NỘI TRÚ 1 CHẠM - RIKKEI HOSPITAL ===");
            System.out.println("1. Xem danh sách giường đang trống");
            System.out.println("2. Tiếp nhận bệnh nhân mới");
            System.out.println("3. Thoát hệ thống");
            System.out.print("Chọn chức năng (1-3): ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAvailableBeds();
                    break;
                case "2":
                    handleAdmission();
                    break;
                case "3":
                    System.out.println("Đang tắt hệ thống. Tạm biệt!");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private void displayAvailableBeds() {
        System.out.println("\n--- DANH SÁCH GIƯỜNG TRỐNG ---");
        List<String> beds = controller.getAvailableBeds();
        if (beds.isEmpty()) {
            System.out.println("Hiện tại khoa đã hết giường trống!");
        } else {
            System.out.println(String.join(", ", beds));
        }
    }

    private void handleAdmission() {
        System.out.println("\n--- TIẾP NHẬN BỆNH NHÂN MỚI ---");

        System.out.print("Nhập tên bệnh nhân: ");
        String name = scanner.nextLine().trim();

        int age = 0;
        while (true) {
            try {
                System.out.print("Nhập tuổi: ");
                age = Integer.parseInt(scanner.nextLine().trim());
                if (age <= 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Tuổi phải là một số nguyên dương. Vui lòng nhập lại!");
            }
        }

        System.out.print("Nhập mã giường muốn xếp: ");
        String bedId = scanner.nextLine().trim();

        double amount = 0;
        while (true) {
            try {
                System.out.print("Nhập số tiền tạm ứng (VNĐ): ");
                amount = Double.parseDouble(scanner.nextLine().trim());
                if (amount < 0) throw new NumberFormatException();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Số tiền không hợp lệ. Vui lòng nhập bằng số!");
            }
        }

        System.out.println("Đang xử lý giao dịch vào hệ thống...");
        boolean isSuccess = controller.processAdmission(name, age, bedId, amount);

        if (isSuccess) {
            System.out.println("=> TIẾP NHẬN THÀNH CÔNG! Đã cập nhật hồ sơ, xếp giường và thu tiền.");
        } else {
            System.out.println("=> TIẾP NHẬN THẤT BẠI! Vui lòng kiểm tra lại thông báo lỗi ở trên.");
        }
    }

    public static void main(String[] args) {
        new ReceptionView().startMenu();
    }
}
