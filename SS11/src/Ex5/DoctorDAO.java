package Ex5;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> list = new ArrayList<>();
        String sql = "SELECT * FROM Doctors";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Doctor(
                        rs.getString("doctor_id"),
                        rs.getString("doctor_name"),
                        rs.getString("specialty")
                ));
            }
        }
        return list;
    }

    public boolean addDoctor(Doctor doc) throws SQLException {
        String sql = "INSERT INTO Doctors (doctor_id, doctor_name, specialty) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, doc.getId());
            ps.setString(2, doc.getName());
            ps.setString(3, doc.getSpecialty());

            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    public Map<String, Integer> getSpecialtyStatistics() throws SQLException {
        Map<String, Integer> stats = new HashMap<>();
        String sql = "SELECT specialty, COUNT(doctor_id) AS total FROM Doctors GROUP BY specialty";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                stats.put(rs.getString("specialty"), rs.getInt("total"));
            }
        }
        return stats;
    }
}
