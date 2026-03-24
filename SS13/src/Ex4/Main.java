// 1. Phân tích Input / Output
//  Input: Tham số truy vấn có thể là ngayHienTai (Date) hoặc maKhoa (String/int). Trong bài này giả định ta lấy toàn bộ bệnh nhân cấp cứu hiện tại.
//  Output: Một List<BenhNhanDTO>. Mỗi phần tử chứa thông tin cơ bản của bệnh nhân và một danh sách List<DichVu> dsDichVu (có thể rỗng nếu chưa dùng dịch vụ nào).
//2. Đề xuất 2 giải pháp truy vấn dứt điểm N+1
//  Thay vì lặp 500 lần để bắn 500 câu query (N+1), chúng ta có 2 cách tối ưu:
//  Giải pháp 1: Sử dụng 1 câu truy vấn với LEFT JOIN và gom nhóm bằng Java (Map)
//  Viết 1 câu SQL LEFT JOIN bảng BenhNhan với DichVuSuDung. Cơ sở dữ liệu sẽ trả về một bảng kết quả phẳng (flat table). Nếu bệnh nhân có 5 dịch vụ, thông tin bệnh nhân đó sẽ lặp lại 5 lần trên 5 dòng, kèm theo 5 dịch vụ khác nhau.
//  Tại tầng Java, sử dụng cấu trúc dữ liệu LinkedHashMap để duyệt qua ResultSet, gom các dịch vụ vào đúng Object BenhNhanDTO duy nhất.
//  Giải pháp 2: Lấy theo lô (Batch) với 2 câu truy vấn và mệnh đề IN (...)
//Query 1: Lấy danh sách 500 bệnh nhân. Quét lấy toàn bộ danh sách ID.
//Query 2: Bắn 1 câu SQL lấy dịch vụ: SELECT * FROM DichVuSuDung WHERE maBenhNhan IN (id1, id2, ..., id500).
//Tại tầng Java, nhóm danh sách dịch vụ trả về theo maBenhNhan và gán ngược lại vào danh sách bệnh nhân ban đầu.

package Ex4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public List<BenhNhanDTO> layDuLieuDashboard() {
        Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();
        String sql = "SELECT b.maBenhNhan, b.tenBenhNhan, b.soGiuong, " +
                "d.maDichVu, d.tenDichVu, d.thoiGianThucHien " +
                "FROM BenhNhan b " +
                "LEFT JOIN DichVuSuDung d ON b.maBenhNhan = d.maBenhNhan " +
                "ORDER BY b.soGiuong ASC";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int maBenhNhan = rs.getInt("maBenhNhan");

                BenhNhanDTO benhNhan = mapBenhNhan.get(maBenhNhan);
                if (benhNhan == null) {
                    benhNhan = new BenhNhanDTO();
                    benhNhan.setMaBenhNhan(maBenhNhan);
                    benhNhan.setTenBenhNhan(rs.getString("tenBenhNhan"));
                    benhNhan.setSoGiuong(rs.getString("soGiuong"));
                    benhNhan.setDsDichVu(new ArrayList<>());

                    mapBenhNhan.put(maBenhNhan, benhNhan);
                }

                // XỬ LÝ BẪY 2: Bẫy mất mát dữ liệu và lỗi NullPointerException
                // =========================================================
                if (rs.getObject("maDichVu") != null) {
                    DichVu dichVu = new DichVu();
                    dichVu.setMaDichVu(rs.getInt("maDichVu"));
                    dichVu.setTenDichVu(rs.getString("tenDichVu"));
                    dichVu.setThoiGianThucHien(rs.getTimestamp("thoiGianThucHien"));

                    benhNhan.getDsDichVu().add(dichVu);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy xuất dữ liệu Dashboard: " + e.getMessage());
        }

        return new ArrayList<>(mapBenhNhan.values());
    }
}
