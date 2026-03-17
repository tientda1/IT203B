package Ex1;

public class Light implements Device {
    @Override
    public void turnOn() { System.out.println("Đèn: Bật sáng."); }
    @Override
    public void turnOff() { System.out.println("Đèn: Đã tắt."); }
}
