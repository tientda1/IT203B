package Ex5;

public class Light {
    private boolean isOn = false;

    public void turnOff() {
        isOn = false;
        System.out.println("Đèn: Tắt");
    }

    public String getStatus() {
        return isOn ? "Đang bật" : "Đang tắt";
    }
}
