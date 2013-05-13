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

    public Promise<List<Order>> getOrdersForUser(String email) {
        return Akka.future(new Callable<List<Order>>() {
            @Override
            public List<Order> call() throws Exception {
                List<Order> result = new ArrayList<>();
                result.add(new Order());
                return result;
            }
        });
    }

    public Promise<List<Product>> getProductsForOrders(List<Order> orders) {
        return Akka.future(new Callable<List<Product>>() {
            @Override
            public List<Product> call() throws Exception {
                List<Product> products = new ArrayList<>();
                products.add(new Product());
                return products;
            }
        });
    }

    public Promise<List<Stock>> getStocksForProducts(List<Product> products) {
        return Akka.future(new Callable<List<Stock>>() {
            @Override
            public List<Stock> call() throws Exception {
                List<Stock> stocks = new ArrayList<>();
                stocks.add(new Stock());
                return stocks;
            }
        });
    }

}
