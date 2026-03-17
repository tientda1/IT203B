package Ex4;

public class Main {
    public static void main(String[] args) {
        TemperatureSensor sensor = new TemperatureSensor();
        Fan fan = new Fan();
        Humidifier humidifier = new Humidifier();

        System.out.println("Đăng ký quạt và máy tạo ẩm");
        sensor.attach(fan);
        System.out.println("Quạt: Đã đăng ký nhận thông báo");

        sensor.attach(humidifier);
        System.out.println("Máy tạo ẩm: Đã đăng ký\n");

        System.out.println("Set nhiệt độ = 18");
        sensor.setTemperature(18);
        System.out.println();

        System.out.println("Set nhiệt độ = 26");
        sensor.setTemperature(26);
    }
}
