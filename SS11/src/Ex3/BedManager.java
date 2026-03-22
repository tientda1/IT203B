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
                // Trả về 0 -> SQL chạy hợp lệ nhưng KHÔNG tìm thấy mã giường này
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
