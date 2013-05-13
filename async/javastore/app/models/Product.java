package models;

public class Product {
    private final String productId;
    private final String description;

    public Product(String productId, String description) {
        this.productId = productId;
        this.description = description;
    }

    public String getProductId() {
        return productId;
    }

    public String getDescription() {
        return description;
    }
}
