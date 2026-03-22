package Ex5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoctorService service = new DoctorService();
        int choice = 0;

        while (true) {
            System.out.println("\n=== HỆ THỐNG RIKKEI-CARE: QUẢN LÝ BÁC SĨ TRỰC CA ===");
            System.out.println("1. Xem danh sách bác sĩ");
            System.out.println("2. Thêm bác sĩ mới");
            System.out.println("3. Thống kê chuyên khoa");
            System.out.println("4. Thoát chương trình");
            System.out.print("Vui lòng chọn chức năng (1-4): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng chỉ nhập số!");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n--- DANH SÁCH BÁC SĨ ---");
                    service.displayAllDoctors();
                    break;
                case 2:
                    System.out.println("\n--- THÊM BÁC SĨ MỚI ---");
                    System.out.print("Nhập mã bác sĩ: ");
                    String id = scanner.nextLine().trim();
                    System.out.print("Nhập họ tên: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Nhập chuyên khoa: ");
                    String spec = scanner.nextLine().trim();

                    service.addNewDoctor(id, name, spec);
                    break;
                case 3:
                    service.displaySpecialtyStats();
                    break;
                case 4:
                    System.out.println("Đã thoát hệ thống Rikkei-Care. Chúc bạn một ngày tốt lành!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println(" Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        }
    }
}
