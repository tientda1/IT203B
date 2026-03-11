package Ex6;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserProfileServiceTest {

    private UserProfileService service;
    private User currentUser;
    private User anotherUser;

    @BeforeEach
    void setUp() {
        service = new UserProfileService();

        currentUser = new User("1", "current@gmail.com", LocalDate.of(1995, 1, 1));
        anotherUser = new User("2", "existing@gmail.com", LocalDate.of(1990, 5, 5));

        service.setDatabase(new ArrayList<>(Arrays.asList(anotherUser)));
    }

    @AfterEach
    void tearDown() {
        service = null;
        currentUser = null;
        anotherUser = null;
    }

    @Test
    void testValidUpdate() {
        User result = service.updateProfile(currentUser, "new@gmail.com", LocalDate.of(1996, 2, 2));
        assertNotNull(result, "Hồ sơ phải được cập nhật thành công");
        assertEquals("new@gmail.com", currentUser.getEmail());
    }

    @Test
    void testFutureDob() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        User result = service.updateProfile(currentUser, "new@gmail.com", futureDate);
        assertNull(result, "Hệ thống phải từ chối khi ngày sinh ở tương lai");
    }

    @Test
    void testDuplicateEmailWithOtherUser() {
        User result = service.updateProfile(currentUser, "existing@gmail.com", LocalDate.of(1995, 1, 1));
        assertNull(result, "Hệ thống phải từ chối khi email trùng với user khác");
    }

    @Test
    void testSameEmailUpdate() {
        User result = service.updateProfile(currentUser, "current@gmail.com", LocalDate.of(1999, 9, 9));
        assertNotNull(result, "Phải cho phép cập nhật thông tin khác nếu email không đổi");
        assertEquals(LocalDate.of(1999, 9, 9), currentUser.getDob());
    }

    @Test
    void testEmptyDatabaseValidUpdate() {
        service.setDatabase(new ArrayList<>()); // Làm rỗng Mock DB
        User result = service.updateProfile(currentUser, "existing@gmail.com", LocalDate.of(1995, 1, 1));
        assertNotNull(result, "Phải cập nhật thành công khi DB không có user nào khác");
    }

    @Test
    void testMultipleViolations() {
        LocalDate futureDate = LocalDate.now().plusDays(10);
        User result = service.updateProfile(currentUser, "existing@gmail.com", futureDate);
        assertNull(result, "Hệ thống phải từ chối khi vi phạm nhiều ràng buộc cùng lúc");
    }
}