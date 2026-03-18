import entity.Product;

public class ProductFactory {
    public static Product createProduct(int type, String id, String name, double price, double extraAttr) {
        switch (type) {
            case 1:
                return new PhysicalProduct(id, name, price, extraAttr);
            case 2:
                return new DigitalProduct(id, name, price, extraAttr);
            default:
                throw new IllegalArgumentException("Loại sản phẩm không hợp lệ!");
        }
    }
}
