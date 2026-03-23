package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Kết nối Database thành công!");

        } catch (ClassNotFoundException e) {
            System.err.println("Lỗi: Không tìm thấy JDBC Driver. Bạn đã tải và add thư viện (.jar) vào project chưa?");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối Database: Sai thông tin URL, Username hoặc Password.");
            e.printStackTrace();
        }
        return conn;
    }
}
