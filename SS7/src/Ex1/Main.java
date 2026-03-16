package Ex1;

import java.util.ArrayList;
import java.util.List;

class Product {
    private String id;
    private String name;
    private double price;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}

class Customer {
    private String name;
    private String email;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
}

class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> items;
    private double totalAmount;

    public Order(String orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }

    public String getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getTotalAmount() { return totalAmount; }
}

class OrderCalculator {
    public void calculateTotal(Order order) {
        System.out.println("Tính tổng tiền");
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        order.setTotalAmount(total);
        System.out.println("Tổng tiền: " + (long) total);
    }
}

class OrderRepository {
    public void save(Order order) {
        System.out.println("Lưu đơn hàng");
        System.out.println("Đã lưu đơn hàng " + order.getOrderId());
    }
}

class EmailService {
    public void sendConfirmationEmail(Order order) {
        System.out.println("Gửi email xác nhận");
        System.out.println("Đã gửi email đến " + order.getCustomer().getEmail() + ": Đơn hàng " + order.getOrderId() + " đã được tạo");
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Tạo sản phẩm: SP01 - Laptop - 15000000, SP02 - Chuột - 300000");
        Product laptop = new Product("SP01", "Laptop", 15000000);
        Product mouse = new Product("SP02", "Chuột", 300000);
        System.out.println("Đã thêm sản phẩm SP01, SP02\n");

        System.out.println("Tạo khách hàng: Nguyễn Văn A - a@example.com");
        Customer customer = new Customer("Nguyễn Văn A", "a@example.com");
        System.out.println("Đã thêm khách hàng\n");

        System.out.println("Tạo đơn hàng: SP01 (1 cái), SP02 (2 cái)");
        Order order = new Order("ORD001", customer);
        order.addItem(laptop, 1);
        order.addItem(mouse, 2);
        System.out.println("Đơn hàng ORD001 được tạo\n");

        OrderCalculator calculator = new OrderCalculator();
        OrderRepository repository = new OrderRepository();
        EmailService emailService = new EmailService();

        calculator.calculateTotal(order);
        System.out.println();

        repository.save(order);
        System.out.println();

        emailService.sendConfirmationEmail(order);
    }
}
