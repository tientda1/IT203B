package Ex6;

// 1. Lớp User cơ bản
class User {
    private String username;

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

// 2. Định nghĩa Functional Interface tùy chỉnh
@FunctionalInterface
interface UserProcessor {
    String process(User u);
}

// 3. Lớp tiện ích (Utility Class) chứa phương thức tĩnh
class UserUtils {
    public static String convertToUpperCase(User u) {
        if (u != null && u.getUsername() != null) {
            return u.getUsername().toUpperCase();
        }
        return "";
    }
}

public class Main {
    public static void main(String[] args) {
        User myUser = new User("nguyen_van_a_2026");
        UserProcessor processor = UserUtils::convertToUpperCase;

        System.out.println("--- Trạng thái ban đầu ---");
        System.out.println("Username gốc: " + myUser.getUsername());

        System.out.println("\n--- Sau khi xử lý qua UserProcessor ---");
        String result = processor.process(myUser);
        System.out.println("Username viết hoa: " + result);
    }
}