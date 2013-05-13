package models;

public class Order {
    private final String productId;
    private final String email;

    public Order(String productId, String email) {
        this.productId = productId;
        this.email = email;
    }

    public String getProductId() {
        return productId;
    }

    public String getEmail() {
        return email;
    }
}
