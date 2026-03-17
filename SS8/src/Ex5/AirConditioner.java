package Ex5;

public class AirConditioner implements Observer {
    private int temperature = 25;

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + this.temperature);
    }

    @Override
    public void update(int currentTemp) {
        // Điều hòa nhận được thông báo nhiệt độ phòng thay đổi nhưng vẫn giữ mức set ban đầu
        System.out.println("Điều hòa: Nhiệt độ = " + this.temperature + " (vẫn giữ, nhưng có thể bổ sung logic nếu muốn)");
    }

    public String getStatus() {
        return "Đang bật, nhiệt độ: " + temperature + "°C";
    }
}
