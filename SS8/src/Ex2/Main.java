package Ex2;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SmartLight light = new SmartLight();
        SmartFan fan = new SmartFan();
        SmartAC ac = new SmartAC();

        OldThermometer oldThermo = new OldThermometer();
        TemperatureSensor sensorAdapter = new ThermometerAdapter(oldThermo);

        SmartHomeFacade homeFacade = new SmartHomeFacade(light, fan, ac, sensorAdapter);

        while (true) {
            System.out.println("\n=== ĐIỀU KHIỂN NHÀ THÔNG MINH (FACADE & ADAPTER) ===");
            System.out.println("1. Xem nhiệt độ hiện tại");
            System.out.println("2. Kích hoạt chế độ Rời nhà");
            System.out.println("3. Kích hoạt chế độ Ngủ");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    homeFacade.getCurrentTemperature();
                    break;
                case 2:
                    homeFacade.leaveHome();
                    break;
                case 3:
                    homeFacade.sleepMode();
                    break;
                case 4:
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }
}
