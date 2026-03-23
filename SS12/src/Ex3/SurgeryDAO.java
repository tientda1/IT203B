package Ex3;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

public class SurgeryDAO {
    public double getSurgeryFee(Connection conn, int surgeryId) {
        String sql = "{call GET_SURGERY_FEE(?, ?)}";
        double totalCost = 0.0;

        try (CallableStatement cstmt = conn.prepareCall(sql)) {

            cstmt.setInt(1, surgeryId);

            cstmt.registerOutParameter(2, Types.DECIMAL);

            cstmt.execute();

            totalCost = cstmt.getDouble(2);

            System.out.println("Tra cứu thành công! Chi phí cho ca phẫu thuật ID " + surgeryId + " là: " + totalCost + " VND");

        } catch (SQLException e) {
            System.err.println("Lỗi khi gọi Stored Procedure: " + e.getMessage());
        }

        return totalCost;
    }
}
