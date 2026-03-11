package Ex6;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class User {
    private String id;
    private String email;
    private LocalDate dob;

    public User(String id, String email, LocalDate dob) {
        this.id = id;
        this.email = email;
        this.dob = dob;
    }

    public String getId() { return id; }
    public String getEmail() { return email; }
    public LocalDate getDob() { return dob; }

    public void setEmail(String email) { this.email = email; }
    public void setDob(LocalDate dob) { this.dob = dob; }
}

class UserProfileService {
    private List<User> database = new ArrayList<>();

    public void setDatabase(List<User> database) {
        this.database = database;
    }

    public User updateProfile(User currentUser, String newEmail, LocalDate newDob) {
        if (newDob != null && newDob.isAfter(LocalDate.now())) {
            return null;
        }

        if (newEmail != null && !newEmail.equals(currentUser.getEmail())) {
            boolean emailExists = database.stream()
                    .anyMatch(u -> !u.getId().equals(currentUser.getId()) && u.getEmail().equals(newEmail));

            if (emailExists) {
                return null;
            }
        }

        currentUser.setEmail(newEmail);
        currentUser.setDob(newDob);
        return currentUser;
    }
}
