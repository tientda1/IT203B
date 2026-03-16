package Ex5;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Product {
    private String id;
    private String name;
    private double price;
    private String category;

    public Product(String id, String name, double price, String category) {
        this.id = id; this.name = name; this.price = price; this.category = category;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}

class Customer {
    private String name;
    private String email;
    private String phone;

    public Customer(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}

class OrderItem {
    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product; this.quantity = quantity;
    }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotal() { return product.getPrice() * quantity; }
}

class Order {
    private String id;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;

    public Order(String id, Customer customer) {
        this.id = id; this.customer = customer;
    }
    public void addItem(Product product, int quantity) {
        items.add(new OrderItem(product, quantity));
    }
    public String getId() { return id; }
    public Customer getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }

    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public double getTotalAmount() { return totalAmount; }

    public void setDiscountAmount(double discountAmount) { this.discountAmount = discountAmount; }
    public double getDiscountAmount() { return discountAmount; }

    public void setFinalAmount(double finalAmount) { this.finalAmount = finalAmount; }
    public double getFinalAmount() { return finalAmount; }
}

interface DiscountStrategy {
    double applyDiscount(double totalAmount);
    String getName();
}

interface PaymentMethod {
    void processPayment(double amount);
    String getName();
}

interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

interface NotificationService {
    void send(String message, String recipient);
}

class PercentageDiscount implements DiscountStrategy {
    private double percentage;
    public PercentageDiscount(double percentage) { this.percentage = percentage; }
    @Override public double applyDiscount(double totalAmount) { return totalAmount * (1 - percentage / 100); }
    @Override public String getName() { return "Giảm " + percentage + "%"; }
}

class FixedDiscount implements DiscountStrategy {
    private double amount;
    public FixedDiscount(double amount) { this.amount = amount; }
    @Override public double applyDiscount(double totalAmount) { return Math.max(0, totalAmount - amount); }
    @Override public String getName() { return "Giảm " + amount + " VNĐ"; }
}

class CreditCardPayment implements PaymentMethod {
    @Override public void processPayment(double amount) { }
    @Override public String getName() { return "Thẻ tín dụng"; }
}

class MomoPayment implements PaymentMethod {
    @Override public void processPayment(double amount) { }
    @Override public String getName() { return "Ví MoMo"; }
}

class DynamicPaymentMethod implements PaymentMethod {
    private String name;
    public DynamicPaymentMethod(String name) { this.name = name; }
    @Override public void processPayment(double amount) { }
    @Override public String getName() { return name; }
}

class DatabaseOrderRepository implements OrderRepository {
    private List<Order> db = new ArrayList<>();
    @Override public void save(Order order) {
        db.add(order);
        System.out.println("Đã lưu đơn hàng " + order.getId());
    }
    @Override public List<Order> findAll() { return db; }
}

class EmailNotification implements NotificationService {
    @Override public void send(String message, String recipient) {
        System.out.println("Đã gửi email đến " + recipient + ": " + message);
    }
}

class InvoiceGenerator {
    public static void printInvoice(Order order) {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("=== HÓA ĐƠN ===");
        System.out.println("Khách: " + order.getCustomer().getName());
        for (OrderItem item : order.getItems()) {
            System.out.printf("%s - Số lượng: %d - Đơn giá: %s - Thành tiền: %s\n",
                    item.getProduct().getId(), item.getQuantity(),
                    df.format(item.getProduct().getPrice()), df.format(item.getTotal()));
        }
        System.out.println("Tổng tiền: " + df.format(order.getTotalAmount()));
        System.out.println("Giảm giá: " + df.format(order.getDiscountAmount()));
        System.out.println("Cần thanh toán: " + df.format(order.getFinalAmount()));
    }
}

class OrderService {
    private OrderRepository repository;
    private NotificationService notificationService;

    public OrderService(OrderRepository repo, NotificationService noti) {
        this.repository = repo;
        this.notificationService = notificationService = noti;
    }

    public void processOrder(Order order, DiscountStrategy discount, PaymentMethod payment) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.getTotal();
        }
        double finalPrice = discount.applyDiscount(total);

        order.setTotalAmount(total);
        order.setDiscountAmount(total - finalPrice);
        order.setFinalAmount(finalPrice);

        InvoiceGenerator.printInvoice(order);

        payment.processPayment(finalPrice);

        repository.save(order);

        notificationService.send("Đơn hàng " + order.getId() + " đã được tạo", order.getCustomer().getEmail());
    }

    public double calculateTotalRevenue() {
        double revenue = 0;
        for (Order o : repository.findAll()) {
            revenue += o.getFinalAmount();
        }
        return revenue;
    }

    public void printAllOrders() {
        DecimalFormat df = new DecimalFormat("#,###");
        System.out.println("Danh sách đơn hàng:");
        for (Order o : repository.findAll()) {
            System.out.println(o.getId() + " - " + o.getCustomer().getName() + " - " + df.format(o.getFinalAmount()));
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        OrderRepository repo = new DatabaseOrderRepository();
        NotificationService emailSvc = new EmailNotification();
        OrderService orderService = new OrderService(repo, emailSvc);

        List<Product> products = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<DiscountStrategy> discounts = new ArrayList<>(List.of(
                new PercentageDiscount(10), new FixedDiscount(50000)
        ));
        List<PaymentMethod> payments = new ArrayList<>(List.of(
                new CreditCardPayment(), new MomoPayment()
        ));

        int orderCounter = 1;

        while (true) {
            System.out.println("\n--- HỆ THỐNG QUẢN LÝ ĐƠN HÀNG ---");
            System.out.println("1. Thêm sản phẩm");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Tạo đơn hàng");
            System.out.println("4. Xem danh sách đơn hàng");
            System.out.println("5. Tính tổng doanh thu");
            System.out.println("6. Thêm phương thức thanh toán mới");
            System.out.println("7. Thêm chiến lược giảm giá mới");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Mã: "); String pId = scanner.nextLine();
                    System.out.print("Tên: "); String pName = scanner.nextLine();
                    System.out.print("Giá: "); double pPrice = Double.parseDouble(scanner.nextLine());
                    System.out.print("Danh mục: "); String pCat = scanner.nextLine();
                    products.add(new Product(pId, pName, pPrice, pCat));
                    System.out.println("Đã thêm sản phẩm " + pId);
                    break;
                case "2":
                    System.out.print("Tên: "); String cName = scanner.nextLine();
                    System.out.print("Email: "); String cEmail = scanner.nextLine();
                    System.out.print("ĐT: "); String cPhone = scanner.nextLine();
                    customers.add(new Customer(cName, cEmail, cPhone));
                    System.out.println("Đã thêm khách hàng");
                    break;
                case "3":
                    if (customers.isEmpty() || products.isEmpty()) {
                        System.out.println("Cần có ít nhất 1 khách hàng và 1 sản phẩm!");
                        break;
                    }
                    Customer selectedCustomer = customers.get(0);
                    Product selectedProduct = products.get(0);

                    String orderId = String.format("ORD%03d", orderCounter++);
                    Order order = new Order(orderId, selectedCustomer);
                    order.addItem(selectedProduct, 1);

                    System.out.println("Tạo đơn hàng: " + selectedProduct.getId() + " (1 cái), Giảm giá: 10%, Thanh toán: Thẻ tín dụng");
                    orderService.processOrder(order, discounts.get(0), payments.get(0));
                    break;
                case "4":
                    orderService.printAllOrders();
                    break;
                case "5":
                    DecimalFormat df = new DecimalFormat("#,###");
                    System.out.println("Tổng doanh thu: " + df.format(orderService.calculateTotalRevenue()));
                    break;
                case "6":
                    System.out.print("Nhập tên phương thức thanh toán mới (VD: ZaloPay): ");
                    String pmName = scanner.nextLine();
                    payments.add(new DynamicPaymentMethod(pmName));
                    System.out.println("Đã thêm phương thức thanh toán " + pmName);
                    break;
                case "7":
                    System.out.print("Nhập % giảm giá cho thành viên VIP (VD: 20): ");
                    double vipPct = Double.parseDouble(scanner.nextLine());
                    discounts.add(new PercentageDiscount(vipPct));
                    System.out.println("Đã thêm chiến lược giảm giá VIP (" + vipPct + "%)");
                    break;
                case "8":
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
