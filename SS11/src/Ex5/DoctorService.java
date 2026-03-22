package Ex5;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

public class DoctorService {
    private DoctorDAO doctorDAO = new DoctorDAO();

    public void displayAllDoctors() {
        try {
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            if (doctors.isEmpty()) {
                System.out.println("(!) Hệ thống hiện chưa có bác sĩ nào.");
                return;
            }
            System.out.printf("%-10s | %-25s | %-20s\n", "Mã BS", "Họ và tên", "Chuyên khoa");
            System.out.println("--------------------------------------------------------------");
            for (Doctor d : doctors) {
                System.out.printf("%-10s | %-25s | %-20s\n", d.getId(), d.getName(), d.getSpecialty());
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi truy xuất danh sách: " + e.getMessage());
        }
    }

    public void addNewDoctor(String id, String name, String specialty) {
        if (id.isEmpty() || name.isEmpty() || specialty.isEmpty()) {
            System.out.println(" Lỗi: Không được bỏ trống các thông tin bắt buộc!");
            return;
        }
        if (specialty.length() > 50) {
            System.out.println(" Lỗi: Tên chuyên khoa quá dài (tối đa 50 ký tự)!");
            return;
        }

        try {
            Doctor newDoc = new Doctor(id, name, specialty);
            boolean success = doctorDAO.addDoctor(newDoc);
            if (success) {
                System.out.println(" THÀNH CÔNG: Đã thêm bác sĩ " + name + " vào hệ thống.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(" LỖI: Mã bác sĩ [" + id + "] đã tồn tại trong hệ thống. Vui lòng nhập mã khác!");
        } catch (SQLException e) {
            System.out.println(" LỖI DB: " + e.getMessage());
        }
    }

    public void displaySpecialtyStats() {
        try {
            Map<String, Integer> stats = doctorDAO.getSpecialtyStatistics();
            if (stats.isEmpty()) {
                System.out.println("(!) Chưa có dữ liệu thống kê.");
                return;
            }
            System.out.println("\n--- THỐNG KÊ BÁC SĨ THEO CHUYÊN KHOA ---");
            for (Map.Entry<String, Integer> entry : stats.entrySet()) {
                System.out.printf("- %-20s: %d bác sĩ\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println(" Lỗi thống kê: " + e.getMessage());
        }
    }
}
