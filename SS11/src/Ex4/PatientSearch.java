// Phân tích
//1. Câu lệnh SQL thực tế sau khi nối chuỗi
//  Khi biến patientName mang giá trị "' OR '1'='1", chuỗi truy vấn hoàn chỉnh được ứng dụng gửi xuống Database sẽ biến đổi thành:
//  SELECT * FROM Patients WHERE full_name = '' OR '1'='1'
//2. Tại sao mệnh đề WHERE lại luôn đúng (True)?
//  Hệ quản trị CSDL sẽ quét qua từng dòng trong bảng Patients và đánh giá điều kiện WHERE. Lúc này, mệnh đề đã bị chia làm 2 vế bởi toán tử OR:
//  Vế 1 (full_name = ''): Thường trả về FALSE (sai) vì hiếm có bệnh nhân nào có tên rỗng.
//  Vế 2 ('1'='1'): Luôn luôn trả về TRUE (đúng) vì mặt logic toán học, 1 chắc chắn bằng 1.
//  Theo quy tắc của toán tử logic OR, chỉ cần một trong hai vế đúng thì toàn bộ biểu thức sẽ đúng (FALSE OR TRUE = TRUE). Do đó, mệnh đề WHERE trả về TRUE đối với tất cả các dòng hiện có trong bảng.
//3. Hậu quả luồng thực thi
//  Thay vì truy xuất đúng một bệnh nhân theo tên được nhập, Database hiểu lệnh này là: "hãy lấy ra dữ liệu nếu tên người đó rỗng HOẶC 1 bằng 1". Vì vế sau luôn đúng, CSDL sẽ trả về toàn bộ danh sách bệnh nhân đang có, dẫn đến việc rò rỉ dữ liệu y tế nghiêm trọng.


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
