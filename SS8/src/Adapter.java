public class Adapter {
}

class YoungHuman {
    void move2foot(){
        System.out.println("Đang đi bằng 2 chân");
    }
}

interface OldHuman {
    void move3foot();
}

class HumanAdapter implements OldHuman {
    private YoungHuman old;
    public HumanAdapter(YoungHuman old) {
        this.old = old;
    }

    @Override
    public void move3foot(){
        old.move2foot();
        // Mở rộng
        System.out.println("Chuyển sang đi bằng gậy");
    }
}
