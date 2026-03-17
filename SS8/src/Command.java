public class Command {
    public static void main(String[] args) {
        Light light = new Light();
        LightCommand on = new TurnOnLight(light);
        LightCommand off = new TurnOffLight(light);

        RemoteLight remoteLight = new RemoteLight();
        remoteLight.setLightCommand(on);
        remoteLight.pressPowerButton();
        remoteLight.pressUndoButton();
    }
}

class Light{
    void turnOn(){
        System.out.println("Đèn đã bật: blink blink");
    }

    public void turnOff(){
        System.out.println("Đèn đã tắt: .........");
    }
}

// Lệnh
interface LightCommand{
    void execute(); // Thực thi
    void undo();    // Hoàn tác
}

class TurnOnLight implements LightCommand{
    private Light light;
    public TurnOnLight(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }
    @Override
    public void undo() {
        light.turnOff();
    }
}

class TurnOffLight implements LightCommand{
    private Light light;
    public TurnOffLight(Light light){
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }
    @Override
    public void undo() {
        light.turnOn();
    }
}

class RemoteLight{
    private LightCommand lightCommand;
    public void setLightCommand(LightCommand lightCommand){
        this.lightCommand = lightCommand;
    }

    public void pressPowerButton(){
        lightCommand.execute();
    }
    public void pressUndoButton(){
        lightCommand.undo();
    }
}
