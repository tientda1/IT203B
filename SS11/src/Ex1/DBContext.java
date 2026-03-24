// Phân tích
//1. Về kỹ thuật:
//  Đoạn code bị lỗi rò rỉ kết nối (Connection Leak) do liên tục mở (getConnection) mà không đóng.
//  Các kết nối cũ không được giải phóng sẽ làm cạn kiệt giới hạn kết nối tối đa (max_connections) của Database, đồng thời làm tràn RAM/CPU khiến hệ thống từ chối các yêu cầu mới và bị "treo".
//
//2. Sự nguy hiểm với hệ thống Y tế:
//  Chậm trễ cấp cứu: Bác sĩ không thể truy xuất bệnh án (nhóm máu, tiền sử dị ứng...) ngay lập tức khi hệ thống sập, đe dọa tính mạng bệnh nhân.
//  Nguy cơ mất dữ liệu: Nếu hệ thống treo đúng lúc đang nhập kết quả xét nghiệm, dữ liệu có thể bị lỗi hoặc mất mát.
//  Phá vỡ tính liên tục: Quản lý kết nối lỏng lẻo buộc đội IT phải khởi động lại server liên tục, phá vỡ nguyên tắc hoạt động xuyên suốt 24/7 của bệnh viện.


package Ex1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContext {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void getPatientRecord(String patientId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBContext.getConnection();

            String sql = "SELECT * FROM patients WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, patientId);

            rs = ps.executeQuery();

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String note = rs.getString("condition_note");

                System.out.println("=== HỒ SƠ BỆNH NHÂN ===");
                System.out.println("Mã BN: " + patientId);
                System.out.println("Họ tên: " + name);
                System.out.println("Tuổi: " + age);
                System.out.println("Ghi chú y tế: " + note);
                System.out.println("=======================\n");
            }

            if (!hasData) {
                System.out.println("Không tìm thấy dữ liệu cho mã bệnh nhân: " + patientId);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi trong quá trình truy xuất: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("[Hệ thống] Đã đóng kết nối Database an toàn.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Đang kết nối đến hệ thống Hospital_DB...\n");

        DBContext db = new DBContext();

        db.getPatientRecord("BN01");
    }
}