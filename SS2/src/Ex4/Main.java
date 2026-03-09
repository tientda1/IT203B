package Ex4;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

class User {
    private String username;

    public User() {
        this.username = "New_User";
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}

public class Main {
    public static void main(String[] args) {

        // 3. Tham chiếu Constructor (User::new)
        Supplier<User> userSupplier = User::new;

        List<User> users = Arrays.asList(
                new User("Alice_Admin"),
                new User("Bob_Manager"),
                userSupplier.get()
        );

        System.out.println("--- Danh sách Username ---");

        users.stream()
                // 1. Tham chiếu Instance method của kiểu cụ thể (User::getUsername)
                .map(User::getUsername)

                // 2. Tham chiếu Instance method của đối tượng cụ thể (System.out::println)
                .forEach(System.out::println);
    }
}
