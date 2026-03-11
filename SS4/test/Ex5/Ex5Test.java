package Ex5;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccessControlServiceTest {

    private AccessControlService accessControlService;
    private User adminUser;
    private User moderatorUser;
    private User normalUser;

    @BeforeEach
    void setUp() {
        accessControlService = new AccessControlService();
        adminUser = new User(Role.ADMIN);
        moderatorUser = new User(Role.MODERATOR);
        normalUser = new User(Role.USER);
    }

    @AfterEach
    void tearDown() {
        accessControlService = null;
        adminUser = null;
        moderatorUser = null;
        normalUser = null;
    }

    @Test
    void testAdminPermissions() {
        assertTrue(accessControlService.canPerformAction(adminUser, Action.DELETE_USER),
                "ADMIN phải có quyền DELETE_USER");

        assertTrue(accessControlService.canPerformAction(adminUser, Action.LOCK_USER),
                "ADMIN phải có quyền LOCK_USER");

        assertTrue(accessControlService.canPerformAction(adminUser, Action.VIEW_PROFILE),
                "ADMIN phải có quyền VIEW_PROFILE");
    }

    @Test
    void testModeratorPermissions() {
        assertFalse(accessControlService.canPerformAction(moderatorUser, Action.DELETE_USER),
                "MODERATOR KHÔNG được có quyền DELETE_USER");

        assertTrue(accessControlService.canPerformAction(moderatorUser, Action.LOCK_USER),
                "MODERATOR phải có quyền LOCK_USER");

        assertTrue(accessControlService.canPerformAction(moderatorUser, Action.VIEW_PROFILE),
                "MODERATOR phải có quyền VIEW_PROFILE");
    }

    @Test
    void testUserPermissions() {
        assertFalse(accessControlService.canPerformAction(normalUser, Action.DELETE_USER),
                "USER KHÔNG được có quyền DELETE_USER");

        assertFalse(accessControlService.canPerformAction(normalUser, Action.LOCK_USER),
                "USER KHÔNG được có quyền LOCK_USER");

        assertTrue(accessControlService.canPerformAction(normalUser, Action.VIEW_PROFILE),
                "USER phải có quyền VIEW_PROFILE");
    }
}