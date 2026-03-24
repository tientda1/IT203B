// Kịch bản lỗi
//1. Vi phạm Khóa chính (Primary Key Violation):
//  Kịch bản: Người dùng nhập Mã bác sĩ (ví dụ: DOC001) đã tồn tại sẵn trong bảng Doctors.
//  Hệ quả: Database từ chối bản ghi. Java sẽ ném ra ngoại lệ SQLIntegrityConstraintViolationException (Lỗi Duplicate entry).
//2. Tràn dữ liệu (Data Truncation):
//  Kịch bản: Nhập tên bác sĩ hoặc chuyên khoa có độ dài vượt quá giới hạn thiết kế của cột (ví dụ: gõ chuỗi 100 ký tự vào cột chỉ cho phép VARCHAR(50)).
//  Hệ quả: Hệ thống báo lỗi SQLException: Data too long for column.
//3. Sai định dạng dữ liệu (Type Mismatch / Format Error):
//  Kịch bản: Nhập chữ cái vào trường yêu cầu số (như số điện thoại, năm sinh), hoặc nhập sai chuẩn ngày tháng (nhập 24/03/2026 trong khi SQL mặc định yêu cầu chuẩn YYYY-MM-DD).
//  Hệ quả: Lỗi InputMismatchException (từ đối tượng Scanner của Java) hoặc SQLException: Incorrect date value từ CSDL.
//4. Vi phạm ràng buộc rỗng (NOT NULL Constraint):
//  Kịch bản: Bỏ qua không nhập liệu (nhấn Enter lướt qua) ở các trường bắt buộc phải có dữ liệu như Tên bác sĩ.
//  Hệ quả: Ném ra lỗi Column cannot be null.
//5. Mất kết nối đột ngột (Connection Drop):
//  Kịch bản: Ứng dụng Console đang treo ở menu chờ lệnh, nhưng Database server bị khởi động lại hoặc rớt mạng. Khi chọn chức năng, hệ thống không kết nối được.
//  Hệ quả: Báo lỗi Communications link failure.





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
