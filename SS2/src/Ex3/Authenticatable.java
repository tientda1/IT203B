package Ex3;

@FunctionalInterface
public interface Authenticatable {

    String getPassword();

    default boolean isAuthenticated() {
        String password = getPassword();
        return password != null && !password.trim().isEmpty();
    }

    static String encrypt(String rawPassword) {
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu cần mã hóa không được để trống");
        }
        return "TEST_" + rawPassword + "_ONE_TWO";
    }
}
