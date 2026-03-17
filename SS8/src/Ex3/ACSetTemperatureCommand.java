package Ex3;

class ACSetTemperatureCommand implements Command {
    private AirConditioner ac;
    private int newTemperature;
    private int previousTemperature;

    public ACSetTemperatureCommand(AirConditioner ac, int temp) {
        this.ac = ac;
        this.newTemperature = temp;
    }

    @Override
    public void execute() {
        previousTemperature = ac.getTemperature();
        ac.setTemperature(newTemperature);
    }

    @Override
    public void undo() {
        ac.setTemperature(previousTemperature);
    }
}
