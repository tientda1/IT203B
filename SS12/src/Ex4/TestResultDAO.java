package Ex4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TestResultDAO {
    public void insertTestResults(Connection conn, List<TestResult> list) {
        String sql = "INSERT INTO Results(data) VALUES (?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int count = 0;
            long startTime = System.currentTimeMillis();
            for (TestResult tr : list) {
                pstmt.setString(1, tr.getData());

                pstmt.executeUpdate();
                count++;
            }

            long endTime = System.currentTimeMillis();
            System.out.println("Đã nạp thành công " + count + " bản ghi.");
            System.out.println("Thời gian thực thi: " + (endTime - startTime) + " ms");

        } catch (SQLException e) {
            System.err.println("Lỗi nạp kết quả xét nghiệm: " + e.getMessage());
        }
    }
}

class TestResult {
    private String data;
    public TestResult(String data) { this.data = data; }
    public String getData() { return data; }
}
