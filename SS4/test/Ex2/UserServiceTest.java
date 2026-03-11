package Ex2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Test
    @DisplayName("Kiểm tra giá trị biên hợp lệ (đủ 18 tuổi)")
    public void testValidBoundaryAge() {
        UserService userService = new UserService();

        boolean result = userService.checkRegistrationAge(18);

        assertEquals(true, result, "Tuổi 18 phải trả về true (hợp lệ)");
    }

    @Test
    @DisplayName("Kiểm tra nhỏ hơn tuổi yêu cầu (17 tuổi)")
    public void testUnderage() {
        UserService userService = new UserService();

        boolean result = userService.checkRegistrationAge(17);

        assertEquals(false, result, "Tuổi 17 phải trả về false (không hợp lệ)");
    }

    @Test
    @DisplayName("Kiểm tra dữ liệu không hợp lệ (tuổi âm)")
    public void testNegativeAgeThrowsException() {
        UserService userService = new UserService();

        assertThrows(IllegalArgumentException.class, () -> {
            userService.checkRegistrationAge(-1);
        }, "Khi nhập tuổi âm (-1), hệ thống phải ném ra IllegalArgumentException");
    }
}