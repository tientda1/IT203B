package Ex5;

// 1. Định nghĩa Interface UserActions
interface UserActions {
    default void logActivity(String activity) {
        System.out.println("[UserActions] Ghi log người dùng thường: " + activity);
    }
}

// 2. Định nghĩa Interface AdminActions
interface AdminActions {
    default void logActivity(String activity) {
        System.out.println("[AdminActions] Ghi log quản trị viên: " + activity);
    }
}

// 3. Lớp SuperAdmin kế thừa cả 2 interface
class SuperAdmin implements UserActions, AdminActions {

    // BẮT BUỘC ghi đè (override) để giải quyết Diamond Problem
    @Override
    public void logActivity(String activity) {
        System.out.println("--- Bắt đầu ghi log cho SuperAdmin ---");

        AdminActions.super.logActivity(activity);

        UserActions.super.logActivity(activity);

        System.out.println("--- Hoàn tất ghi log ---");
    }
}

// 4. Chương trình chính
public class Main {
    public static void main(String[] args) {
        SuperAdmin superadmin = new SuperAdmin();

        // Thực hiện logActivity
        System.out.println("Thực hiện hành động: Khôi phục dữ liệu hệ thống");
        superadmin.logActivity("Khôi phục dữ liệu hệ thống");
    }
}
