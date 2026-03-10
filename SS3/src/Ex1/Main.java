package Ex1;

import java.util.List;

enum Status {
    ACTIVE, INACTIVE
}

record User(String username, String email, Status status) {}

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("alice", "alice@example.com", Status.ACTIVE),
                new User("bob", "bob@example.com", Status.INACTIVE),
                new User("charlie", "charlie@example.com", Status.ACTIVE)
        );

        System.out.println("Danh sách người dùng:");
        users.forEach(System.out::println);
    }
}
