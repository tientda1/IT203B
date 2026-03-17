import java.util.ArrayList;
import java.util.List;

public class Observer {
    public static void main(String[] args) {
        ReceiverObserver r1 = new ConcreteReceiver("Dương");
        ReceiverObserver r2 = new ConcreteReceiver("An");

        SourceNews vnExpress = new SourceNews();
        vnExpress.addReceiver(r1);
        vnExpress.addReceiver(r2);
        vnExpress.sendNews();
    }
}

// Người nhận là ai
interface ReceiverObserver{
    void update(String news);
}

// Nguồn tin
class SourceNews {
    private List<ReceiverObserver> observers = new ArrayList<>();
    public void addReceiver(ReceiverObserver receiver){
        observers.add(receiver);
    }

    public void sendNews(){
        String content = "afasafs";
        observers.forEach(ob -> {
            ob.update(content);
        });
    }
}

// Người nhận chi tiết
class ConcreteReceiver implements ReceiverObserver{
    private String name;
    public ConcreteReceiver(String name){
        this.name = name;
    }
    @Override
    public void update(String news) {
        System.out.println("Bạn đã nhận được tin" + news);
    }
}
