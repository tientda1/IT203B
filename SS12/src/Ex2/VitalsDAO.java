package Ex2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VitalsDAO {
    public boolean updatePatientVitals(Connection conn, int patientId, double temperature, int heartRate) {
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";
        boolean isSuccess = false;

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, temperature);
            pstmt.setInt(2, heartRate);
            pstmt.setInt(3, patientId);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật thành công chỉ số sinh tồn cho Bệnh nhân ID: " + patientId);
                isSuccess = true;
            } else {
                System.out.println("Không tìm thấy bệnh nhân có ID: " + patientId);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chỉ số sinh tồn: " + e.getMessage());
        }

        return isSuccess;
    }
}
