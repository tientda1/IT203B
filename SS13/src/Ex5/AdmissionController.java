package Ex5;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdmissionController {

    public List<String> getAvailableBeds() {
        List<String> beds = new ArrayList<>();
        String sql = "SELECT bed_id FROM Beds WHERE status = 'AVAILABLE'";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                beds.add(rs.getString("bed_id"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi truy xuất giường: " + e.getMessage());
        }
        return beds;
    }

    public boolean processAdmission(String name, int age, String bedId, double advancePayment) {
        Connection conn = null;
        PreparedStatement psPatient = null;
        PreparedStatement psBed = null;
        PreparedStatement psFinance = null;
        ResultSet rsKeys = null;

        try {
            conn = DatabaseHelper.getConnection();
            conn.setAutoCommit(false);

            String sqlInsertPatient = "INSERT INTO Patients (full_name, age) VALUES (?, ?)";
            psPatient = conn.prepareStatement(sqlInsertPatient, Statement.RETURN_GENERATED_KEYS);
            psPatient.setString(1, name);
            psPatient.setInt(2, age);
            psPatient.executeUpdate();

            int newPatientId = -1;
            rsKeys = psPatient.getGeneratedKeys();
            if (rsKeys.next()) {
                newPatientId = rsKeys.getInt(1);
            } else {
                throw new SQLException("Không thể lấy ID bệnh nhân tự sinh.");
            }

            String sqlUpdateBed = "UPDATE Beds SET status = 'OCCUPIED' WHERE bed_id = ? AND status = 'AVAILABLE'";
            psBed = conn.prepareStatement(sqlUpdateBed);
            psBed.setString(1, bedId);
            int bedRowsAffected = psBed.executeUpdate();

            if (bedRowsAffected == 0) {
                throw new Exception("Giường " + bedId + " không tồn tại hoặc đã có người nằm. Vui lòng chọn giường khác!");
            }

            String sqlInsertFinance = "INSERT INTO Financial_Records (patient_id, amount) VALUES (?, ?)";
            psFinance = conn.prepareStatement(sqlInsertFinance);
            psFinance.setInt(1, newPatientId);
            psFinance.setDouble(2, advancePayment);
            psFinance.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                    System.err.println(">> ĐÃ ROLLBACK: Giao dịch bị hủy do lỗi -> " + e.getMessage());
                } catch (SQLException ex) {
                    System.err.println("Lỗi nghiêm trọng khi Rollback: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (rsKeys != null) rsKeys.close();
                if (psPatient != null) psPatient.close();
                if (psBed != null) psBed.close();
                if (psFinance != null) psFinance.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi dọn dẹp tài nguyên: " + e.getMessage());
            }
        }
    }
}
