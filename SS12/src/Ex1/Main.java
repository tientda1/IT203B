package Ex1;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            DoctorDAO dao = new DoctorDAO();

            System.out.println("--- BẮT ĐẦU TEST SQL INJECTION SHIELD ---");

            dao.login(conn, "DOC01", "123456");

            try {
                conn.close();
                System.out.println("Đã đóng kết nối Database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Không thể kết nối đến Database. Vui lòng kiểm tra lại class utils.DBConnection!");
        }
    }
}