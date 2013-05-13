package models;

import play.libs.Akka;
import play.libs.F;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static play.libs.F.Promise;

public class Backends {

    public Promise<User> getUserById(final String userId) {
        return Akka.future(new Callable<User>() {
            @Override
            public User call() throws Exception {
                return new User(userId, "email@domain.com");
            }
        });
    }

    public Promise<List<Order>> getOrdersForUser(final String email) {
        return Akka.future(new Callable<List<Order>>() {
            @Override
            public List<Order> call() throws Exception {
                List<Order> result = new ArrayList<>();
                result.add(new Order("productA", email));
                return result;
            }
        });
    }

    public Promise<List<Product>> getProductsForOrders(final List<Order> orders) {
        return Akka.future(new Callable<List<Product>>() {
            @Override
            public List<Product> call() throws Exception {
                List<Product> products = new ArrayList<>();
                for (Order order : orders) {
                    products.add(new Product(order.getProductId(), "description"));
                }
                return products;
            }
        });
    }

    public Promise<List<Stock>> getStocksForProducts(final List<Product> products) {
        return Akka.future(new Callable<List<Stock>>() {
            @Override
            public List<Stock> call() throws Exception {
                List<Stock> stocks = new ArrayList<>();
                for (Product product : products) {
                    stocks.add(new Stock(product.getProductId(), 5));
                }
                return stocks;
            }
        });
    }

}
