package BTTH;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManager {
    private List<Product> productList;

    public ProductManager() {
        this.productList = new ArrayList<>();
    }

    public void addProduct(Product product) throws InvalidProductException {
        boolean isDuplicate = productList.stream()
                .anyMatch(p -> p.getId() == product.getId());

        if (isDuplicate) {
            throw new InvalidProductException("Lỗi: Sản phẩm có ID " + product.getId() + " đã tồn tại!");
        }
        productList.add(product);
        System.out.println("Thêm sản phẩm thành công!");
    }

    public void displayProducts() {
        if (productList.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống.");
            return;
        }
        System.out.println("-".repeat(75));
        System.out.printf("| %-5s | %-20s | %-10s | %-10s | %-10s |\n", "ID", "Tên sản phẩm", "Giá", "Số lượng", "Danh mục");
        System.out.println("-".repeat(75));

        productList.forEach(p -> {
            System.out.printf("| %-5d | %-20s | %-10.2f | %-10d | %-10s |\n",
                    p.getId(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategory());
        });
        System.out.println("-".repeat(75));
    }

    public void updateQuantity(int id, int newQuantity) throws InvalidProductException {
        Optional<Product> productOptional = productList.stream()
                .filter(p -> p.getId() == id)
                .findFirst();

        if (productOptional.isPresent()) {
            productOptional.get().setQuantity(newQuantity);
            System.out.println("Cập nhật số lượng thành công!");
        } else {
            throw new InvalidProductException("Lỗi: Không tìm thấy sản phẩm có ID " + id);
        }
    }

    public void deleteOutOfStock() {
        boolean isRemoved = productList.removeIf(p -> p.getQuantity() == 0);
        if (isRemoved) {
            System.out.println("Đã dọn dẹp các sản phẩm có số lượng = 0 trong kho.");
        } else {
            System.out.println("Không có sản phẩm nào hết hàng.");
        }
    }
}
