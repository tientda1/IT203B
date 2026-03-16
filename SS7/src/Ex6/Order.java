package Ex6;

public class Order {
    private String id;
    private Product product;
    private double totalAmount;

    public Order(String id, Product product) {
        this.id = id;
        this.product = product;
        this.totalAmount = product.getPrice();
    }

    public String getId() { return id; }
    public Product getProduct() { return product; }
    public double getTotalAmount() { return totalAmount; }
}
