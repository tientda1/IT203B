package Ex2;

import java.util.List;

enum Status {
    ACTIVE, INACTIVE
}

record User(String username, String email, Status status) {}

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@gmail.com", Status.ACTIVE),
                new User("bob", "bob@yahoo.com", Status.INACTIVE),
                new User("charlie", "charlie@gmail.com", Status.ACTIVE),
                new User("david", "david@outlook.com", Status.ACTIVE)
        );

        System.out.println("Danh sách username sử dụng Gmail:");

        users.stream()
                .filter(user -> user.email().endsWith("@gmail.com"))
                .forEach(user -> System.out.println(user.username()));
    }
}
