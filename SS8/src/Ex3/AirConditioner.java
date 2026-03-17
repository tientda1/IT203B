package Ex3;

public class AirConditioner {
    private int temperature = 25;

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temp);
    }

    public int getTemperature() {
        return temperature;
    }
}
