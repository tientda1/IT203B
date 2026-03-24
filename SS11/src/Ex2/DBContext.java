// Phân tích
//1.Tại sao dùng if không đủ để in danh sách?
//  Lệnh if chỉ kiểm tra điều kiện và thực thi khối lệnh bên trong một lần duy nhất.
//  Khi dùng if (rs.next()), chương trình chỉ lấy và in ra được bản ghi đầu tiên (nếu bảng có dữ liệu) rồi thoát khỏi khối điều kiện. Để duyệt qua toàn bộ các loại thuốc trong kho, bắt buộc phải dùng cấu trúc vòng lặp (cụ thể là while).
//2. Con trỏ của ResultSet hoạt động ra sao sau mỗi lần gọi next()?
//  Trạng thái ban đầu: Khi ResultSet vừa được khởi tạo, con trỏ dữ liệu nằm ở vị trí trước dòng đầu tiên (Before First). Lúc này bạn chưa thể lấy dữ liệu.
//  Hành vi của next(): Mỗi khi gọi rs.next(), con trỏ sẽ dịch chuyển xuống đúng một dòng tiếp theo.
//  Giá trị trả về: Nếu dòng hiện tại có chứa dữ liệu, hàm trả về true. Nếu con trỏ đã vượt qua dòng dữ liệu cuối cùng (After Last) hoặc bảng vốn dĩ trống rỗng, hàm trả về false.




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
