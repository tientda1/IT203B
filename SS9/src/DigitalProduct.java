import entity.Product;

public class DigitalProduct extends Product {
    private double size;

    public DigitalProduct(String id, String name, double price, double size) {
        super(id, name, price);
        this.size = size;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public void displayInfo() {
        System.out.printf("Digital Product  - ID: %s | Name: %s | Price: %.2f | Size: %.2f MB\n",
                id, name, price, size);
    }
}
