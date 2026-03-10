package Ex5;

import java.util.Comparator;
import java.util.List;

enum Status {
    ACTIVE, INACTIVE
}

record User(String username, String email, Status status) {}

public class Main {
    public static void main(String[] args) {
        List<User> users = List.of(
                new User("bob", "bob@example.com", Status.ACTIVE),
                new User("alexander", "alex@example.com", Status.ACTIVE),
                new User("alice", "alice@example.com", Status.ACTIVE),
                new User("charlotte", "char@example.com", Status.INACTIVE),
                new User("benjamin", "ben@example.com", Status.ACTIVE),
                new User("charlie", "charlie@example.com", Status.INACTIVE)
        );

        System.out.println("3 người dùng có username dài nhất:");

        users.stream()
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())
                .limit(3)
                .map(user -> user.username())
                .forEach(username -> System.out.println(username));
    }
}
