package Ex1;

public class AirConditioner implements Device {
    @Override
    public void turnOn() { System.out.println("Điều hòa: Bật làm mát."); }
    @Override
    public void turnOff() { System.out.println("Điều hòa: Đã tắt."); }
}
