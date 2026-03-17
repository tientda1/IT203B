package Ex3;

public class Main {
    public static void main(String[] args) {
        Light livingRoomLight = new Light();
        AirConditioner bedroomAC = new AirConditioner();

        RemoteControl remote = new RemoteControl(5);

        System.out.println("=== CÀI ĐẶT REMOTE ===");

        remote.setCommand(1, new LightOnCommand(livingRoomLight), "LightOnCommand");

        remote.setCommand(2, new LightOffCommand(livingRoomLight), "LightOffCommand");

        remote.setCommand(3, new ACSetTemperatureCommand(bedroomAC, 36), "ACSetTempCommand(26)");

        System.out.println("\n=== SỬ DỤNG REMOTE ===");

        System.out.println("Nhấn nút 1");
        remote.pressButton(1);

        System.out.println("\nNhấn nút 2");
        remote.pressButton(2);


        System.out.println("\nNhấn Undo");
        remote.pressUndo();

        System.out.println("\nNhấn nút 3");
        remote.pressButton(3);

        System.out.println("\nNhấn Undo");
        remote.pressUndo();
    }
}
