// 1. Phân tích bài toán (I/O)
//  Dữ liệu đầu vào (Input):
//  maBenhNhan (int): Mã định danh duy nhất của bệnh nhân cần làm thủ tục xuất viện.
//  tienVienPhi (double): Tổng số tiền viện phí cần thanh toán.
//  Kết quả trả về (Output):
//  Hàm được thiết kế kiểu void (hoặc boolean nếu cần trả kết quả cho Frontend). Trong phạm vi bài này, hàm sẽ in ra thông báo ở Console:
//  Thành công: Xác nhận đã trừ tiền, giải phóng giường và đổi trạng thái bệnh nhân.
//  Thất bại: In ra nguyên nhân lỗi cụ thể (do thiếu tiền, sai mã bệnh nhân, hoặc lỗi hệ thống) kèm thông báo đã Rollback thành công.
//2. Đề xuất giải pháp
//  Sử dụng chung một đối tượng Connection cho toàn bộ quá trình và tắt chế độ Auto-Commit (conn.setAutoCommit(false)).
//  Xử lý Bẫy 1 (Thiếu tiền): Thực hiện một câu lệnh SELECT để kiểm tra số dư hiện tại trước khi thực hiện lệnh UPDATE. Nếu soDu < tienVienPhi, chủ động dùng lệnh throw new Exception(...) để ép luồng chương trình nhảy vào khối catch, qua đó kích hoạt conn.rollback().
//  Xử lý Bẫy 2 (Dữ liệu ảo): Lợi dụng giá trị trả về của hàm executeUpdate(). Hàm này trả về một số nguyên (int) đại diện cho số dòng dữ liệu bị thay đổi (Row Affected). Nếu kết quả trả về là 0, nghĩa là câu lệnh SQL chạy đúng cú pháp nhưng không tìm thấy maBenhNhan nào khớp điều kiện WHERE. Lúc này, ta tiếp tục throw new SQLException(...) để ép Rollback toàn bộ các thao tác trước đó.
//3. Thiết kế các bước thực thi tuần tự
//  Mở kết nối: Lấy Connection và setAutoCommit(false).
//  Bước 1 (Kiểm tra Bẫy 1): Truy vấn số dư tạm ứng của maBenhNhan. So sánh với tienVienPhi. Ném ngoại lệ nếu không đủ.
//  Bước 2 (Trừ tiền): Thực thi lệnh UPDATE giảm số dư ví. Kiểm tra Row Affected (Bẫy 2).
//  Bước 3 (Giải phóng giường): Thực thi lệnh UPDATE trạng thái giường thành "Trống". Kiểm tra Row Affected (Bẫy 2).
//  Bước 4 (Cập nhật hồ sơ): Thực thi lệnh UPDATE trạng thái bệnh nhân thành "Đã xuất viện". Kiểm tra Row Affected (Bẫy 2).
//  Xác nhận (Commit): Nếu vượt qua toàn bộ các bước trên, gọi conn.commit().
//  Xử lý lỗi (Catch - Rollback): Bắt các ngoại lệ được ném ra, gọi conn.rollback() và in thông báo.
//  Dọn dẹp (Finally): Đóng các PreparedStatement, ResultSet và Connection.


package Ex3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psCheckBalance = null;
        PreparedStatement psUpdateWallet = null;
        PreparedStatement psFreeBed = null;
        PreparedStatement psUpdatePatient = null;
        ResultSet rs = null;

        try {
            conn = DatabaseManager.getConnection();
            // Bắt đầu Transaction
            conn.setAutoCommit(false);

            // BẪY 1 - KIỂM TRA LOGIC NGHIỆP VỤ (THIẾU TIỀN)
            String sqlCheckBalance = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            psCheckBalance = conn.prepareStatement(sqlCheckBalance);
            psCheckBalance.setInt(1, maBenhNhan);
            rs = psCheckBalance.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < tienVienPhi) {
                    throw new Exception("Lỗi nghiệp vụ: Số dư trong ví (" + currentBalance +
                            ") không đủ để thanh toán viện phí (" + tienVienPhi + ").");
                }
            } else {
                throw new SQLException("Lỗi dữ liệu: Không tìm thấy ví của bệnh nhân mã " + maBenhNhan);
            }

            // BƯỚC 1: TRỪ TIỀN TRONG VÍ & XỬ LÝ BẪY 2 (ROW AFFECTED = 0)
            String sqlUpdateWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            psUpdateWallet = conn.prepareStatement(sqlUpdateWallet);
            psUpdateWallet.setDouble(1, tienVienPhi);
            psUpdateWallet.setInt(2, maBenhNhan);

            int rowsWalletUpdated = psUpdateWallet.executeUpdate();
            if (rowsWalletUpdated == 0) {
                throw new SQLException("Lỗi Row Affected: Không thể cập nhật ví. Bệnh nhân không tồn tại.");
            }

            // BƯỚC 2: GIẢI PHÓNG GIƯỜNG BỆNH
            String sqlFreeBed = "UPDATE Hospital_Beds SET status = 'AVAILABLE', patient_id = NULL WHERE patient_id = ?";
            psFreeBed = conn.prepareStatement(sqlFreeBed);
            psFreeBed.setInt(1, maBenhNhan);

            int rowsBedUpdated = psFreeBed.executeUpdate();
            if (rowsBedUpdated == 0) {
                throw new SQLException("Lỗi Row Affected: Không tìm thấy giường đang được bệnh nhân này sử dụng.");
            }

            // BƯỚC 3: CẬP NHẬT TRẠNG THÁI BỆNH NHÂN (XUẤT VIỆN)
             String sqlUpdatePatient = "UPDATE Patients SET status = 'DISCHARGED' WHERE patient_id = ?";
            psUpdatePatient = conn.prepareStatement(sqlUpdatePatient);
            psUpdatePatient.setInt(1, maBenhNhan);

            int rowsPatientUpdated = psUpdatePatient.executeUpdate();
            if (rowsPatientUpdated == 0) {
                throw new SQLException("Lỗi Row Affected: Không thể cập nhật trạng thái hồ sơ bệnh nhân.");
            }

            // HOÀN TẤT GIAO DỊCH
            conn.commit();
            System.out.println("Giao dịch thành công: Bệnh nhân " + maBenhNhan + " đã xuất viện và thanh toán " + tienVienPhi);

        } catch (Exception e) {
            System.out.println("Xảy ra sự cố: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("-> Đã Rollback toàn bộ giao dịch, đảm bảo an toàn dữ liệu.");
                } catch (SQLException ex) {
                    System.out.println("Lỗi hệ thống khi thực hiện Rollback: " + ex.getMessage());
                }
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (psCheckBalance != null) psCheckBalance.close();
                if (psUpdateWallet != null) psUpdateWallet.close();
                if (psFreeBed != null) psFreeBed.close();
                if (psUpdatePatient != null) psUpdatePatient.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi khi giải phóng tài nguyên: " + ex.getMessage());
            }
        }
    }
}
