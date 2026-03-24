// Phân tích
//1. Ý nghĩa giá trị trả về của executeUpdate()
//  Khi thực thi các lệnh thay đổi dữ liệu (INSERT, UPDATE, DELETE), phương thức executeUpdate() không trả về tập dữ liệu mà trả về một số nguyên (int).
//  Con số này chính là số lượng bản ghi (rows) đã bị tác động trong database.
//  Việc cập nhật một bed_id không tồn tại không phải là lỗi cú pháp hay kết nối, nên Java sẽ không ném ra ngoại lệ (Exception). Nó chỉ đơn giản thực thi lệnh và trả về giá trị 0 (vì không tìm thấy dòng nào để update). Việc đoạn code cũ bỏ qua giá trị này dẫn đến việc luôn in ra thông báo "thành công" một cách mù quáng.
//2. Cách xử lý để phản hồi chính xác cho y tá
//  Để khắc phục, hứng giá trị trả về và kiểm tra bằng điều kiện rẽ nhánh:
//  Gán kết quả: int rowsAffected = stmt.executeUpdate(sql);
//  Kiểm tra if (rowsAffected > 0): Thông báo cập nhật thành công.
//  Kiểm tra else (hoặc rowsAffected == 0): Báo lỗi "Mã giường này không tồn tại!".



package Ex3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BedManager {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void updateBedStatus(String inputId) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = BedManager.getConnection();

            String sql = "UPDATE Beds SET bed_status = 'Đang sử dụng' WHERE bed_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, inputId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("THÀNH CÔNG: Đã cập nhật giường [" + inputId + "] sang trạng thái 'Đang sử dụng'.");
            } else {
                System.out.println(" LỖI: Cập nhật thất bại! Mã giường [" + inputId + "] KHÔNG TỒN TẠI trong hệ thống.");
            }

        } catch (SQLException e) {
            System.out.println(" Lỗi hệ thống Database: " + e.getMessage());
        } finally {
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        BedManager manager = new BedManager();

        System.out.println("--- HỆ THỐNG QUẢN LÝ NHẬP VIỆN ---\n");

        System.out.print("Kịch bản 1 (Nhập đúng): ");
        manager.updateBedStatus("Bed_01");

        System.out.println("----------------------------------");

        System.out.print("Kịch bản 2 (Nhập sai):  ");
        manager.updateBedStatus("Bed_999");
    }
}
