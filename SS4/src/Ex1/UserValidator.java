package Ex1;

public class UserValidator {

    public boolean isValidUsername(String username) {
        if (username == null) {
            return false;
        }

        boolean hasValidLength = username.length() >= 6 && username.length() <= 20;

        boolean hasNoSpaces = !username.contains(" ");

        return hasValidLength && hasNoSpaces;
    }
}
