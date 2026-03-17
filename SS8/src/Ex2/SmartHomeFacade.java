package Ex2;

public class SmartHomeFacade {
    private SmartLight light;
    private SmartFan fan;
    private SmartAC ac;
    private TemperatureSensor tempSensor;

    public SmartHomeFacade(SmartLight light, SmartFan fan, SmartAC ac, TemperatureSensor tempSensor) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
        this.tempSensor = tempSensor;
    }

    public void leaveHome() {
        light.turnOff();
        fan.turnOff();
        ac.turnOff();
    }

    public void sleepMode() {
        light.turnOff();
        ac.setTemperature(28);
        fan.setLowSpeed();
    }

    public void getCurrentTemperature() {
        double tempC = tempSensor.getTemperatureCelsius();
        double formattedTempC = Math.floor(tempC * 10) / 10;
        System.out.println("Nhiệt độ hiện tại: " + formattedTempC + "°C (chuyển đổi từ 78°F)");
    }
}
