package Ex1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDAO {
    public boolean login(Connection conn, String code, String pass) {
        String sql = "SELECT * FROM Doctors WHERE doctor_id = ? AND password = ?";
        boolean isSuccess = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, code);
            pstmt.setString(2, pass);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Đăng nhập thành công! Hệ thống đã xác thực Bác sĩ.");
                    isSuccess = true;
                } else {
                    System.out.println("Đăng nhập thất bại: Sai mã bác sĩ hoặc mật khẩu.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu trong quá trình đăng nhập: " + e.getMessage());
        }

        return isSuccess;
    }
}
