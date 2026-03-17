package Ex3;

class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.turnOn(); }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        light.turnOff();
    }
}
