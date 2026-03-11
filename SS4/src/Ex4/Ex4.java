package Ex4;

public class Ex4 {
    public String evaluatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return "Yếu";
        }
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[^a-zA-Z0-9].*");
        int count = 0;
        if (hasUpper) count++;
        if (hasLower) count++;
        if (hasDigit) count++;
        if (hasSpecial) count++;
        if (count == 4) {
            return "Mạnh";
        }
        if (count >= 2) {
            return "Trung bình";
        }
        return "Yếu";
    }
}
