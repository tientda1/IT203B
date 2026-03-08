import java.util.Scanner;

public class Ex2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("--- HỆ THỐNG CHIA NHÓM NGƯỜI DÙNG ---");

        System.out.print("Vui lòng nhập tổng số người dùng: ");
        int totalUsers = scanner.nextInt();

        System.out.print("Vui lòng nhập số lượng nhóm muốn chia: ");
        int numGroups = scanner.nextInt();

        try {
            int usersPerGroup = totalUsers / numGroups;
            System.out.println("Thành công! Mỗi nhóm sẽ có " + usersPerGroup + " người.");

        } catch (ArithmeticException e) {
            System.out.println("Lỗi: Không thể chia cho 0!");
        }

        System.out.println("-> Luồng lệnh phía sau vẫn tiếp tục hoạt động bình thường.");
        System.out.println("--- KẾT THÚC CHƯƠNG TRÌNH ---");

        scanner.close();
    }
}