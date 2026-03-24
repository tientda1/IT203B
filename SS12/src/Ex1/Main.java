// Phân tích:
// Vì sao PreparedStatement là "tấm khiên"?
//  1. Tách SQL và dữ liệu
//  SQL: SELECT * FROM Doctors WHERE code = ? AND pass = ?
//  Dữ liệu truyền riêng → không thể phá cấu trúc
//  2. Cơ chế Pre-compiled (biên dịch trước)
//  DB:
//  Parse & compile SQL trước
//  Khóa cấu trúc query
//  Sau đó mới nhận giá trị
//  Input không thể:
//  Thêm OR
//  Thêm điều kiện
//  Thay đổi logic




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