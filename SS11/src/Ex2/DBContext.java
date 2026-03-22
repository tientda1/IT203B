package Ex2;

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

    public void printPharmacyCatalogue() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBContext.getConnection();

            String sql = "SELECT medicine_name, stock_quantity FROM medicines";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            System.out.println("=== BẢNG KIỂM KÊ KHO THUỐC ===");
            System.out.printf("%-5s | %-25s | %-15s\n", "STT", "Tên loại thuốc", "Số lượng tồn kho");
            System.out.println("---------------------------------------------------");

            int count = 0;

            while (rs.next()) {
                count++;
                String name = rs.getString("medicine_name");
                int quantity = rs.getInt("stock_quantity");

                System.out.printf("%-5d | %-25s | %-15d\n", count, name, quantity);
            }

            if (count == 0) {
                System.out.println("(!) CẢNH BÁO: Kho thuốc hiện tại đang trống! Không có dữ liệu.");
            }
            System.out.println("===================================================\n");

        } catch (SQLException e) {
            System.out.println("Lỗi truy xuất hệ thống: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        System.out.println("Hệ thống kiểm kê đang khởi động...\n");
        DBContext db = new DBContext();
        db.printPharmacyCatalogue();
    }
}
