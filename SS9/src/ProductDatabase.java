import entity.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    private static ProductDatabase instance;
    private List<Product> products;

    private ProductDatabase() {
        products = new ArrayList<>();
    }

    public static ProductDatabase getInstance() {
        if (instance == null) {
            instance = new ProductDatabase();
        }
        return instance;
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Thêm sản phẩm thành công!");
    }

    public void updateProduct(String id, String newName, double newPrice) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                p.setName(newName);
                p.setPrice(newPrice);
                System.out.println("Cập nhật thông tin thành công!");
                return;
            }
        }
        System.out.println("Không tìm thấy sản phẩm với ID: " + id);
    }

    public void deleteProduct(String id) {
        Product toRemove = null;
        for (Product p : products) {
            if (p.getId().equals(id)) {
                toRemove = p;
                break;
            }
        }
        if (toRemove != null) {
            products.remove(toRemove);
            System.out.println("Xóa sản phẩm thành công!");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + id);
        }
    }

    public List<Product> getAllProducts() {
        return products;
    }
}
