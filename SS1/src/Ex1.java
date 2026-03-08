import java.util.Scanner;
import java.time.Year;

public class Ex1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Vui lòng nhập năm sinh của bạn: ");
            String input = scanner.nextLine();
            int birthYear = Integer.parseInt(input);

            int currentYear = Year.now().getValue();
            int age = currentYear - birthYear;

            System.out.println("Đăng ký thành công! Tuổi của bạn là: " + age);

        } catch (NumberFormatException e) {
            System.out.println("Lỗi: Dữ liệu nhập vào không hợp lệ. Vui lòng chỉ nhập số cho năm sinh (ví dụ: 1995).");

        } finally {
            scanner.close();
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
        }
    }
}
