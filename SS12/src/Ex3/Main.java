package Ex3;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            SurgeryDAO dao = new SurgeryDAO();

            System.out.println("--- BẮT ĐẦU TEST STORED PROCEDURE ---");

            int targetSurgeryId = 505;
            System.out.println("Đang tra cứu chi phí cho ca phẫu thuật ID: " + targetSurgeryId + "...");

            dao.getSurgeryFee(conn, targetSurgeryId);

            try {
                conn.close();
                System.out.println("Đã đóng kết nối Database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Lỗi: Không thể kết nối đến Database!");
        }
    }
}
