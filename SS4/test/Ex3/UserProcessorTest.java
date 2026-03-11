package Ex3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserProcessorTest {

    private UserProcessor userProcessor;

    @BeforeEach
    public void setUp() {
        userProcessor = new UserProcessor();
    }

    @Test
    @DisplayName("Kiểm tra email hợp lệ có ký tự @ và tên miền")
    public void testValidEmailReturnsSameString() {
        String result = userProcessor.processEmail("user@gmail.com");

        assertEquals("user@gmail.com", result, "Email hợp lệ phải trả về đúng chuỗi đầu vào");
    }

    @Test
    @DisplayName("Kiểm tra email thiếu ký tự @")
    public void testEmailMissingAtSymbolThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userProcessor.processEmail("usergmail.com");
        }, "Email không có @ phải ném ra IllegalArgumentException");
    }

    @Test
    @DisplayName("Kiểm tra email có @ nhưng không có tên miền")
    public void testEmailMissingDomainThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userProcessor.processEmail("user@");
        }, "Email có @ nhưng thiếu tên miền phải ném ra IllegalArgumentException");
    }

    @Test
    @DisplayName("Kiểm tra chuẩn hóa email (chuyển về lowercase)")
    public void testEmailNormalizationToLowerCase() {
        String result = userProcessor.processEmail("Example@Gmail.com");

        assertEquals("example@gmail.com", result, "Email phải được chuẩn hóa hoàn toàn về chữ thường");
    }
}