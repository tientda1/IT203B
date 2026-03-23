package Ex4;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            TestResultDAO dao = new TestResultDAO();

            List<TestResult> dummyList = new ArrayList<>();
            for (int i = 1; i <= 1000; i++) {
                dummyList.add(new TestResult("Ket_qua_mau_" + i));
            }

            System.out.println("--- BẮT ĐẦU TEST HIỆU NĂNG INSERT ---");
            System.out.println("Đang chuẩn bị nạp " + dummyList.size() + " bản ghi vào Database...");

            dao.insertTestResults(conn, dummyList);

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
