package Ex4;

import java.util.ArrayList;
import java.util.List;

class Order {
    private String id;

    public Order(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

interface NotificationService {
    void send(String message, String recipient);
}

class FileOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào file: " + order.getId());
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

class DatabaseOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào database: " + order.getId());
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>();
    }
}

class EmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("  Gửi email: " + message);
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("  Gửi SMS: " + message);
    }
}

class OrderService {
    private OrderRepository orderRepository;
    private NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.notificationService = notificationService;
    }

    public void processOrder(Order order, String customerContact) {
        orderRepository.save(order);
        String message = "Đơn hàng " + order.getId() + " đã được tạo";
        notificationService.send(message, customerContact);
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Cấu hình 1: Dùng FileOrderRepository và EmailService ---");
        OrderRepository fileRepo = new FileOrderRepository();
        NotificationService emailSvc = new EmailService();

        OrderService service1 = new OrderService(fileRepo, emailSvc);

        Order order1 = new Order("ORD001");
        service1.processOrder(order1, "user@example.com");


        System.out.println("\n--- Cấu hình 2: Đổi sang DatabaseOrderRepository và SMSNotification ---");
        OrderRepository dbRepo = new DatabaseOrderRepository();
        NotificationService smsSvc = new SMSNotification();

        OrderService service2 = new OrderService(dbRepo, smsSvc);

        Order order2 = new Order("ORD002");
        service2.processOrder(order2, "0901234567");
    }
}
