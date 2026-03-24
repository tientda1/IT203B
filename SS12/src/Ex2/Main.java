// Phân tích
// 1. Không truyền dạng chuỗi
//  PreparedStatement gửi dữ liệu theo kiểu nhị phân (binary), không phải text
//  DB nhận đúng kiểu:
//  double -> số thực
//  int -> số nguyên
//  2. Không phụ thuộc Locale
//  Không có chuyện: dấu . hay ,
//  Driver JDBC tự xử lý đúng chuẩn DB
//  3. Tách biệt hoàn toàn:
//  SQL: cấu trúc
//  Data: giá trị
//->Không format -> không lỗi



package Ex2;

import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Connection conn = DBConnection.getConnection();

        if (conn != null) {
            VitalsDAO dao = new VitalsDAO();

            System.out.println("--- BẮT ĐẦU TEST CẬP NHẬT CHỈ SỐ SINH TỒN ---");

            double newTemp = 39.5;
            int newHeartRate = 105;
            int patientId = 1;

            dao.updatePatientVitals(conn, patientId, newTemp, newHeartRate);

            try {
                conn.close();
                System.out.println("Đã đóng kết nối Database.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Lỗi: Không thể kết nối Database!");
        }
    }
}
