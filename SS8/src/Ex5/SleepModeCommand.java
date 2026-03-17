package Ex5;

public class SleepModeCommand implements Command {
    private Light light;
    private AirConditioner ac;
    private Fan fan;

    public SleepModeCommand(Light light, AirConditioner ac, Fan fan) {
        this.light = light;
        this.ac = ac;
        this.fan = fan;
    }

    @Override
    public void execute() {
        System.out.println("SleepMode: Tắt đèn");
        System.out.println("SleepMode: Điều hòa set 28°C");
        System.out.println("SleepMode: Quạt tốc độ thấp");

        light.turnOff();
        ac.setTemperature(28);
        fan.setLowSpeed();
    }
}
