public class Facade {
    public static void main(String[] args) {
        FacadeRestaurant facadeRestaurant = new FacadeRestaurant();
        facadeRestaurant.order();
        facadeRestaurant.pay();
    }
}

class FacadeRestaurant{
    private Chef chef = new Chef();
    private Staff staff = new Staff();
    private Manager manager = new Manager();
    void order(){
        staff.order();
        chef.cook();
    }

    void pay(){
        staff.invoice();
        manager.feedback();
        manager.complainToChef();
    }
}

class Chef{
    public void cook(){
        System.out.println("Nhận order từ bồi bàn");
        System.out.println("Nấu món ăn");
        System.out.println("-----------------");
    }
}

class Staff {
    public void order(){
        System.out.println("Order món ăn cho khách");
    }
    public void invoice(){
        System.out.println("Gửi hóa đơn cho khách");
    }
}

class Manager{
    public void feedback(){
        System.out.println("Hỏi ý kiến khách về trải nghiệm món ăn");
    }

    public void complainToChef(){
        System.out.println("Chửi đầu bếp");
    }
}
