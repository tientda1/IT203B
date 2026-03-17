package Ex4;

public class Fan implements Observer {
    @Override
    public void update(int temperature) {
        if (temperature < 20) {
            System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
        } else if (temperature >= 20 && temperature <= 25) {
            System.out.println("Quạt: Nhiệt độ trung bình, chạy tốc độ vừa");
        } else {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
        }
    }
}