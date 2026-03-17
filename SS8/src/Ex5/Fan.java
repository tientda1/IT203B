package Ex5;

public class Fan implements Observer {
    private String speed = "Tắt";

    public void setLowSpeed() {
        this.speed = "Tốc độ thấp";
        System.out.println("Quạt: Chạy tốc độ thấp");
    }

    @Override
    public void update(int temperature) {
        if (temperature > 30) {
            System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
            this.speed = "Tốc độ mạnh";
        }
    }

    public String getStatus() {
        return "Tốc độ hiện tại: " + speed;
    }
}
