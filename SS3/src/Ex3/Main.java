package Ex3;
import java.util.List;
import java.util.Optional;

enum Status {
    ACTIVE, INACTIVE
}

record User(String username, String email, Status status) {}

class UserRepository {
    private final List<User> users = List.of(
            new User("alice", "alice@gmail.com", Status.ACTIVE),
            new User("bob", "bob@yahoo.com", Status.INACTIVE),
            new User("charlie", "charlie@gmail.com", Status.ACTIVE)
    );

    public Optional<User> findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.username().equals(username))
                .findFirst();
    }
}

public class Main {
    public static void main(String[] args) {
        UserRepository repo = new UserRepository();

        Optional<User> optionalUser = repo.findUserByUsername("alice");

        String loginMessage = optionalUser
                .map(user -> "Welcome " + user.username())
                .orElse("Guest login");
        System.out.println(loginMessage);

        System.out.println("---");
    }
}
