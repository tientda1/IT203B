package Ex3;

public class UserProcessor {

    public String processEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email không hợp lệ: Thiếu ký tự '@'");
        }

        String[] parts = email.split("@", -1);

        if (parts[1].trim().isEmpty()) {
            throw new IllegalArgumentException("Email không hợp lệ: Thiếu tên miền sau '@'");
        }

        return email.toLowerCase();
    }
}
