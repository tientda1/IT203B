// Phân tích
// Mỗi vòng lặp (1000 lần), DB phải:
//  Parse (phân tích SQL)
//  Kiểm tra cú pháp
//  Tách câu lệnh
//  Optimize (tối ưu)
//  Tạo Execution Plan
//  Compile (biên dịch)
//  Chuẩn bị thực thi
//  Tổng cộng: 1000 lần làm lại cùng 1 việc
//  Vấn đề cốt lõi:
//  Câu SQL giống nhau về cấu trúc
//  Chỉ khác giá trị data
//  Nhưng DB vẫn phải:
//  Parse -> Optimize -> Execute  (lặp lại 1000 lần)
//  Hậu quả:
//  CPU DB tăng cao
//  Tốn thời gian xử lý
//  Hiệu năng cực chậm khi dữ liệu lớn



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
