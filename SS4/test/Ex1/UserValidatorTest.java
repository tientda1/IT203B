package Ex1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {

    @Test
    @DisplayName("TC01: Username hợp lệ (Độ dài chuẩn, không khoảng trắng)")
    public void testValidUsername() {
        UserValidator validator = new UserValidator();
        String input = "user123";

        boolean result = validator.isValidUsername(input);

        assertTrue(result, "Username 'user123' phải được đánh giá là hợp lệ (true)");
    }

    @Test
    @DisplayName("TC02: Username không hợp lệ (Quá ngắn, dưới 6 ký tự)")
    public void testUsernameTooShort() {
        UserValidator validator = new UserValidator();
        String input = "abc";

        boolean result = validator.isValidUsername(input);

        assertFalse(result, "Username 'abc' quá ngắn, phải được đánh giá là không hợp lệ (false)");
    }

    @Test
    @DisplayName("TC03: Username không hợp lệ (Chứa khoảng trắng)")
    public void testUsernameContainsSpace() {
        UserValidator validator = new UserValidator();
        String input = "user name";

        boolean result = validator.isValidUsername(input);

        assertFalse(result, "Username 'user name' chứa khoảng trắng, phải được đánh giá là không hợp lệ (false)");
    }
}