package Ex5;

public class Main {
    public static void main(String[] args) {
        User user = new User();

        System.out.println("--- HỆ THỐNG QUẢN LÝ NGƯỜI DÙNG ---");

        try {
            System.out.println("Đang thiết lập tuổi: 20...");
            user.setAge(20);
            System.out.println("-> Cập nhật thành công! Tuổi hiện tại: " + user.getAge());

            System.out.println("\nĐang thiết lập tuổi: -2...");
            user.setAge(-2);

        } catch (InvalidAgeException e) {
            System.out.println("\n[!] BẮT LỖI NGHIỆP VỤ:");
            System.out.println("Loại ngoại lệ: " + e.getClass().getName());
            System.out.println("Chi tiết lỗi: " + e.getMessage());

            System.out.println("\n--- Stack Trace ---");
            e.printStackTrace();
        }

        System.out.println("\n--- KẾT THÚC CHƯƠNG TRÌNH ---");
    }
}
