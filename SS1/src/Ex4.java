import java.io.IOException;

public class Ex4 {

    public static void main(String[] args) {
        System.out.println("--- BẮT ĐẦU CHƯƠNG TRÌNH (Method A) ---");
        System.out.println("Tiến hành gọi Method B...\n");

        try {
            processUserData();

        } catch (IOException e) {
            System.out.println("\n[!] BẮT LỖI TẠI METHOD A (main):");
            System.out.println("Chi tiết lỗi: " + e.getMessage());
            System.out.println("Đã xử lý sự cố an toàn, ngăn chặn ứng dụng bị crash.");
        }

        System.out.println("\n-> Các luồng lệnh khác vẫn tiếp tục chạy.");
        System.out.println("--- KẾT THÚC CHƯƠNG TRÌNH ---");
    }

    public static void processUserData() throws IOException {
        System.out.println("Đang xử lý dữ liệu người dùng tại Method B...");

        saveToFile();

        System.out.println("Xử lý dữ liệu hoàn tất tại Method B.");
    }

    public static void saveToFile() throws IOException {
        System.out.println("Bắt đầu tiến trình lưu file tại Method C...");

        boolean isDiskFull = true;

        if (isDiskFull) {
            System.out.println(">> (Method C) Phát hiện ổ cứng đầy, đang ném ra IOException...");
            throw new IOException("Không thể ghi file do dung lượng ổ đĩa đã đầy!");
        }

        System.out.println("Lưu file thành công!");
    }
}
