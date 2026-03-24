// Phân tích:
// Khi bạn thiết lập conn.setAutoCommit(false), bạn đang nói với Database rằng: "Hãy giữ tất cả các thay đổi lại, đừng lưu vĩnh viễn cho đến khi tôi gọi commit()".
//Khi lỗi SQLException xảy ra ở Thao tác 2 (do sai tên bảng), luồng chương trình lập tức nhảy vào khối catch. Lúc này, hệ thống đang ở trạng thái:
//Dữ liệu bị "treo": Thao tác 1 (trừ tiền) đã chạy trong bộ nhớ đệm của Database, bảng Patient_Wallet đang bị khóa (lock) ở dòng dữ liệu đó, chờ quyết định cuối cùng.
//Lãng phí tài nguyên: Việc bạn chỉ dùng System.out.println() giống như việc bạn thông báo có cháy nhưng không gọi xe cứu hỏa. Khối catch chạy xong, hàm kết thúc, nhưng Database không nhận được tín hiệu kết thúc giao dịch (commit hoặc rollback).
//Hậu quả: Kết nối (Connection) này nếu được trả về Connection Pool sẽ mang theo một giao dịch đang dang dở. Các truy vấn sau này dùng lại kết nối đó có thể bị ảnh hưởng, hoặc các giao dịch khác muốn cập nhật ví của bệnh nhân này sẽ bị treo cứng (Deadlock) vì chờ lock được giải phóng.
//Hành động thiết yếu bị bỏ quên: Lập trình viên đã quên gọi lệnh conn.rollback() để yêu cầu Database chủ động hủy bỏ hoàn toàn những thay đổi tạm thời (trừ tiền ví) và giải phóng các tài nguyên/khóa (locks) đang giữ.


public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
    Connection conn = null;
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;

    try {
        conn = DatabaseManager.getConnection();

        conn.setAutoCommit(false);

        String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
        ps1 = conn.prepareStatement(sqlDeductWallet);
        ps1.setDouble(1, amount);
        ps1.setInt(2, patientId);
        ps1.executeUpdate();

        String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
        ps2 = conn.prepareStatement(sqlUpdateInvoice);
        ps2.setInt(1, invoiceId);
        ps2.executeUpdate();

        conn.commit();
        System.out.println("Thanh toán hoàn tất!");

    } catch (SQLException e) {
        System.out.println("Lỗi hệ thống: Không thể hoàn tất thanh toán. Chi tiết: " + e.getMessage());

        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("Đã hủy bỏ (rollback) các thay đổi, tiền trong ví được bảo toàn.");
            } catch (SQLException ex) {
                System.out.println("Lỗi nghiêm trọng khi đang cố gắng rollback: " + ex.getMessage());
            }
        }
    } finally {
        try {
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi khi đóng kết nối Database: " + ex.getMessage());
        }
    }
}
