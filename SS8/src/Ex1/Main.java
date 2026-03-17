package Ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Device> deviceList = new ArrayList<>();
        HardwareConnection hardware = null;

        while (true) {
            System.out.println("\n=== HỆ THỐNG NHÀ THÔNG MINH ===");
            System.out.println("1. Kết nối phần cứng");
            System.out.println("2. Tạo thiết bị mới");
            System.out.println("3. Bật thiết bị");
            System.out.println("4. Tắt thiết bị");
            System.out.println("5. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    hardware = HardwareConnection.getInstance();
                    hardware.connect();
                    break;

                case 2:
                    System.out.println("Chọn loại: 1. Đèn, 2. Quạt, 3. Điều hòa");
                    System.out.print("Chọn: ");
                    int type = scanner.nextInt();

                    DeviceFactory factory = null;
                    if (type == 1) factory = new LightFactory();
                    else if (type == 2) factory = new FanFactory();
                    else if (type == 3) factory = new AirConditionerFactory();
                    else System.out.println("Lựa chọn không hợp lệ.");

                    if (factory != null) {
                        Device newDevice = factory.createDevice();
                        deviceList.add(newDevice);
                    }
                    break;

                case 3:
                case 4:
                    if (deviceList.isEmpty()) {
                        System.out.println("Hệ thống chưa có thiết bị nào. Vui lòng tạo thiết bị trước!");
                        break;
                    }
                    System.out.print("Chọn thiết bị (từ 1 đến " + deviceList.size() + "): ");
                    int deviceIndex = scanner.nextInt() - 1;

                    if (deviceIndex >= 0 && deviceIndex < deviceList.size()) {
                        Device selectedDevice = deviceList.get(deviceIndex);
                        if (choice == 3) {
                            selectedDevice.turnOn();
                        } else {
                            selectedDevice.turnOff();
                        }
                    } else {
                        System.out.println("Không tìm thấy thiết bị này.");
                    }
                    break;

                case 5:
                    if (hardware != null) {
                        hardware.disconnect();
                    }
                    System.out.println("Thoát chương trình.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Chức năng không tồn tại.");
            }
        }
    }
}
