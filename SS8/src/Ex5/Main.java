package Ex5;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Light light = new Light();
        AirConditioner ac = new AirConditioner();
        Fan fan = new Fan();

        TemperatureSensor sensor = new TemperatureSensor();
        sensor.attach(fan);
        sensor.attach(ac);

        Command sleepCommand = new SleepModeCommand(light, ac, fan);

        while (true) {
            System.out.println("\n=== HỆ THỐNG NHÀ THÔNG MINH ===");
            System.out.println("1. Kích hoạt chế độ ngủ (Sleep Mode)");
            System.out.println("2. Giả lập thay đổi nhiệt độ (Cảm biến báo về)");
            System.out.println("3. Xem trạng thái thiết bị");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n--- Đang chuyển sang Chế độ Ngủ ---");
                    sleepCommand.execute();
                    break;

                case 2:
                    System.out.print("Nhập nhiệt độ môi trường hiện tại (°C): ");
                    int temp = scanner.nextInt();
                    System.out.println("\n--- Cập nhật cảm biến ---");
                    sensor.setTemperature(temp);
                    break;

                case 3:
                    System.out.println("\n--- Trạng thái thiết bị ---");
                    System.out.println("Đèn: " + light.getStatus());
                    System.out.println("Quạt: " + fan.getStatus());
                    System.out.println("Điều hòa: " + ac.getStatus());
                    break;

                case 4:
                    System.out.println("Tắt hệ thống.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
