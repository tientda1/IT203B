package btth;

import utils.DBConnection;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        String senderId = "ACC01";
        String receiverId = "ACC02";
        double transferAmount = 1500.00;

        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            if (conn == null) {
                System.out.println("Không thể thiết lập kết nối đến cơ sở dữ liệu. Chương trình kết thúc.");
                return;
            }

            conn.setAutoCommit(false);
            System.out.println("Đã bắt đầu Transaction...");

            String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, senderId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        double currentBalance = rs.getDouble("Balance");
                        if (currentBalance < transferAmount) {
                            System.out.println("Lỗi: Tài khoản người gửi không đủ số dư.");
                            conn.rollback();
                            return;
                        }
                    } else {
                        System.out.println("Lỗi: Không tìm thấy tài khoản người gửi.");
                        conn.rollback();
                        return;
                    }
                }
            }

            String callSql = "{CALL sp_UpdateBalance(?, ?)}";
            try (CallableStatement callStmt = conn.prepareCall(callSql)) {
                callStmt.setString(1, senderId);
                callStmt.setDouble(2, -transferAmount);
                callStmt.executeUpdate();
                System.out.println("- Đã trừ " + transferAmount + " từ tài khoản " + senderId);

                callStmt.setString(1, receiverId);
                callStmt.setDouble(2, transferAmount);
                callStmt.executeUpdate();
                System.out.println("- Đã cộng " + transferAmount + " vào tài khoản " + receiverId);
            }

            conn.commit();
            System.out.println("Chuyển khoản thành công! Transaction đã được commit.\n");

            String resultSql = "SELECT AccountId, FullName, Balance FROM Accounts WHERE AccountId IN (?, ?)";
            try (PreparedStatement resultStmt = conn.prepareStatement(resultSql)) {
                resultStmt.setString(1, senderId);
                resultStmt.setString(2, receiverId);
                try (ResultSet rsFinal = resultStmt.executeQuery()) {
                    System.out.println("--- KẾT QUẢ CUỐI CÙNG ---");
                    System.out.printf("%-15s | %-20s | %-15s\n", "Mã Tài Khoản", "Tên Khách Hàng", "Số Dư Mới");
                    System.out.println("-------------------------------------------------------");
                    while (rsFinal.next()) {
                        System.out.printf("%-15s | %-20s | %-15.2f\n",
                                rsFinal.getString("AccountId"),
                                rsFinal.getString("FullName"),
                                rsFinal.getDouble("Balance"));
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Có lỗi SQL xảy ra trong quá trình giao dịch. Đang thực hiện Rollback...");
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Rollback thành công. Dữ liệu được khôi phục nguyên trạng.");
                }
            } catch (SQLException ex) {
                System.out.println("Lỗi nghiêm trọng khi cố gắng rollback: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.setAutoCommit(true);
                    conn.close();
                    System.out.println("Đã đóng kết nối Database.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}