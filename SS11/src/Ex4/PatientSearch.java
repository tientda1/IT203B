package Ex4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientSearch {

    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void searchPatient(String inputName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            String safeName = inputName.replace("'", "")
                    .replace(";", "")
                    .replace("--", "");

            System.out.println("Chuỗi gốc từ người dùng: " + inputName);
            System.out.println("Chuỗi sau khi làm sạch:  " + safeName);

            conn = PatientSearch.getConnection();
            stmt = conn.createStatement();

            String sql = "SELECT * FROM Patients WHERE name = '" + safeName + "'";
            System.out.println("Câu lệnh SQL thực thi:   " + sql + "\n");

            rs = stmt.executeQuery(sql);

            int count = 0;
            System.out.println("--- KẾT QUẢ TÌM KIẾM ---");
            while (rs.next()) {
                count++;
                String id = rs.getString("id");
                String name = rs.getString("name");
                System.out.println("- Bệnh nhân: " + name + " (Mã: " + id + ")");
            }

            if (count == 0) {
                System.out.println("Không tìm thấy bệnh nhân nào khớp với tên này.");
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy xuất: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        PatientSearch searcher = new PatientSearch();

        String hackerInput = "' OR '1'='1";

        System.out.println("Hệ thống đang xử lý yêu cầu tìm kiếm...\n");
        searcher.searchPatient(hackerInput);
    }
}
