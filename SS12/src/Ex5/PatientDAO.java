package Ex5;

import java.sql.*;

public class PatientDAO {
    public void displayAllPatients(Connection conn) {
        String sql = "SELECT patient_id, full_name, age, department FROM Patients";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("\n--- DANH SÁCH BỆNH NHÂN ---");
            System.out.printf("%-5s | %-25s | %-5s | %-20s\n", "ID", "Họ Tên", "Tuổi", "Khoa");
            System.out.println("---------------------------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-5d | %-25s | %-5d | %-20s\n",
                        rs.getInt("patient_id"),
                        rs.getString("full_name"),
                        rs.getInt("age"),
                        rs.getString("department"));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tải danh sách: " + e.getMessage());
        }
    }

    public void addPatient(Connection conn, String name, int age, String department, String details) {
        String sql = "INSERT INTO Patients (full_name, age, department, medical_details) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, age);
            pstmt.setString(3, department);
            pstmt.setString(4, details);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("-> Tiếp nhận thành công bệnh nhân: " + name);
        } catch (SQLException e) {
            System.err.println("Lỗi thêm bệnh nhân: " + e.getMessage());
        }
    }

    public void updateMedicalRecord(Connection conn, int patientId, String newDetails) {
        String sql = "UPDATE Patients SET medical_details = ? WHERE patient_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDetails);
            pstmt.setInt(2, patientId);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("-> Cập nhật bệnh án thành công cho BN mã: " + patientId);
            else System.out.println("-> Không tìm thấy bệnh nhân có mã: " + patientId);
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật bệnh án: " + e.getMessage());
        }
    }

    public void calculateFee(Connection conn, int patientId) {
        String sql = "{call CALCULATE_DISCHARGE_FEE(?, ?)}";
        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, patientId);
            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();
            double totalFee = cstmt.getDouble(2);

            if (totalFee == 0.0) {
                System.out.println("-> Không thể tính phí. Kiểm tra lại mã bệnh nhân.");
            } else {
                System.out.printf("-> Tổng viện phí cần thanh toán cho BN mã %d là: %,.0f VND\n", patientId, totalFee);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi tính viện phí: " + e.getMessage());
        }
    }
}
