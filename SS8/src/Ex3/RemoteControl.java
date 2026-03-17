package Ex3;

import java.util.Stack;

public class RemoteControl {
    private Command[] buttons;
    private Stack<Command> undoStack;

    public RemoteControl(int numberOfButtons) {
        buttons = new Command[numberOfButtons];
        undoStack = new Stack<>();
    }

    public void setCommand(int slot, Command command, String commandName) {
        buttons[slot] = command;
        System.out.println("Đã gán " + commandName + " cho nút " + slot);
    }

    public void pressButton(int slot) {
        if (buttons[slot] != null) {
            buttons[slot].execute();
            undoStack.push(buttons[slot]);
        } else {
            System.out.println("Nút này chưa được gán lệnh!");
        }
    }
    public void pressUndo() {
        if (!undoStack.isEmpty()) {
            Command lastCommand = undoStack.pop();
            lastCommand.undo();
        } else {
            System.out.println("Không có thao tác nào để Undo.");
        }
    }
}
