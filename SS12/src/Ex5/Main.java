package Ex5;

import utils.DBConnection; // Import từ nhà chung
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Lỗi kết nối Database. Vui lòng kiểm tra lại!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        PatientDAO dao = new PatientDAO();

        while (true) {
            System.out.println("\n=== HỆ THỐNG QUẢN LÝ NỘI TRÚ RIKKEI-HOSPITAL ===");
            System.out.println("1. Danh sách bệnh nhân");
            System.out.println("2. Tiếp nhận bệnh nhân mới");
            System.out.println("3. Cập nhật bệnh án");
            System.out.println("4. Xuất viện & Tính phí");
            System.out.println("5. Thoát chương trình");
            System.out.print("Chọn chức năng (1-5): ");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    dao.displayAllPatients(conn);
                    break;
                case 2:
                    System.out.print("Nhập tên bệnh nhân (VD: D'Arcy): ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập tuổi: ");
                    int age = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập khoa điều trị: ");
                    String dept = scanner.nextLine();
                    System.out.print("Nhập tình trạng bệnh lý ban đầu: ");
                    String details = scanner.nextLine();
                    dao.addPatient(conn, name, age, dept, details);
                    break;
                case 3:
                    System.out.print("Nhập mã ID bệnh nhân cần cập nhật: ");
                    int idToUpdate = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nhập thông tin bệnh án mới: ");
                    String newDetails = scanner.nextLine();
                    dao.updateMedicalRecord(conn, idToUpdate, newDetails);
                    break;
                case 4:
                    System.out.print("Nhập mã ID bệnh nhân cần xuất viện: ");
                    int idToDischarge = Integer.parseInt(scanner.nextLine());
                    dao.calculateFee(conn, idToDischarge);
                    break;
                case 5:
                    System.out.println("Đang đóng hệ thống...");
                    try {
                        conn.close();
                        scanner.close();
                        System.out.println("Đã thoát an toàn. Chào tạm biệt!");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại!");
            }
        }
    }
}
