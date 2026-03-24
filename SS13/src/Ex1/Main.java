// Phân tích
//  Nguyên nhân dẫn đến tình trạng thất thoát thuốc (dữ liệu kho bị trừ nhưng không lưu lịch sử) nằm ở cơ chế Auto-Commit mặc định của JDBC kết hợp với việc luồng thực thi bị ngắt quãng bởi Exception.
//  Cụ thể quy trình xảy ra lỗi như sau:
//  Auto-Commit đang bật: Mặc định, khi bạn tạo một Connection trong JDBC, thuộc tính autoCommit luôn được đặt là true. Nghĩa là, mỗi một câu lệnh SQL (như INSERT, UPDATE, DELETE) ngay sau khi chạy xong (executeUpdate()) sẽ lập tức được hệ thống ghi nhận (commit) vĩnh viễn vào Database một cách độc lập.
//  Thao tác 1 thực thi và Commit ngay lập tức: Khi dòng code ps1.executeUpdate(); chạy xong, số lượng thuốc trong kho Medicine_Inventory lập tức bị trừ đi 1 và lưu lại vào ổ cứng của Database do cơ chế Auto-Commit.
//  Exception ngắt luồng (Lỗi chia cho 0): Ngay sau đó, dòng int x = 10 / 0; tung ra lỗi ArithmeticException. Lúc này, chương trình lập tức nhảy vào khối catch.
//  Thao tác 2 bị bỏ qua: Câu lệnh INSERT để lưu lịch sử vào Prescription_History không bao giờ được chạy tới.
//  Hậu quả: Giao dịch (Transaction) bị đứt gãy giữa chừng. Thao tác 1 đã thành công và không thể rút lại (vì đã tự động commit), thao tác 2 không được thực hiện. Điều này phá vỡ tính nguyên tử (Atomicity) của giao dịch: "Hoặc tất cả cùng thành công, hoặc không có gì thay đổi".



public void capPhatThuoc(int medicineId, int patientId) {
    Connection conn = null;
    PreparedStatement ps1 = null;
    PreparedStatement ps2 = null;

    try {
        conn = DatabaseManager.getConnection();

        conn.setAutoCommit(false);

        String sqlUpdateInventory = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
        ps1 = conn.prepareStatement(sqlUpdateInventory);
        ps1.setInt(1, medicineId);
        ps1.executeUpdate();

        String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, GETDATE())";
        ps2 = conn.prepareStatement(sqlInsertHistory);
        ps2.setInt(1, patientId);
        ps2.setInt(2, medicineId);
        ps2.executeUpdate();

        conn.commit();
        System.out.println("Cấp phát thuốc thành công!");

    } catch (Exception e) {
        if (conn != null) {
            try {
                conn.rollback();
                System.out.println("Đã Rollback dữ liệu do phát hiện lỗi trong quá trình cấp phát.");
            } catch (SQLException ex) {
                System.out.println("Lỗi khi đang cố gắng Rollback: " + ex.getMessage());
            }
        }
        System.out.println("Có lỗi xảy ra: " + e.getMessage());
    } finally {
        try {
            if (ps1 != null) ps1.close();
            if (ps2 != null) ps2.close();
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng tài nguyên: " + e.getMessage());
        }
    }
}
