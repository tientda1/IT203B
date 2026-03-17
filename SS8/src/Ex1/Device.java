package Ex1;

public interface Device {
    void turnOn();
    void turnOff();
}

abstract class DeviceFactory {
    public abstract Device createDevice();
}
