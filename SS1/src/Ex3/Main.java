package Ex3;

public class Main {
    public static void main(String[] args) {
        User user = new User();

        System.out.println("--- KIỂM TRA THIẾT LẬP TUỔI ---");

        try {
            System.out.println("Đang thiết lập tuổi: 25...");
            user.setAge(25);
            System.out.println("-> Thành công! Tuổi hiện tại là: " + user.getAge());

            System.out.println("\nĐang thiết lập tuổi: -5...");
            user.setAge(-5);

            System.out.println("-> Cập nhật tuổi thành công!");

        } catch (IllegalArgumentException e) {
            System.out.println("Lỗi nghiệp vụ: " + e.getMessage());
        }

        System.out.println("--- KẾT THÚC ---");
    }
}
