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