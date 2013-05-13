package controllers;

import models.*;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

import static play.libs.F.Promise;

public class ApplicationWithMultipleBackends extends Controller {

    private static final Backends backends = new Backends();

    public static Result index(String userId) {
        Promise<User> user = backends.getUserById(userId);
        Promise<List<Order>> orders = user.flatMap(new F.Function<User, Promise<List<Order>>>() {
            @Override
            public Promise<List<Order>> apply(User user) throws Throwable {
                return backends.getOrdersForUser(user.getEmail());
            }
        });
        Promise<List<Product>> products = orders.flatMap(new F.Function<List<Order>, Promise<List<Product>>>() {
            @Override
            public Promise<List<Product>> apply(List<Order> orders) throws Throwable {
                return backends.getProductsForOrders(orders);
            }
        });
        Promise<List<Stock>> stocks = products.flatMap(new F.Function<List<Product>, Promise<List<Stock>>>() {
            @Override
            public Promise<List<Stock>> apply(List<Product> products) throws Throwable {
                return backends.getStocksForProducts(products);
            }
        });
        Promise<List<Object>> promises = Promise.sequence(user, orders, products, stocks);
        return async(
            promises.map(new F.Function<List<Object>, Result>() {
                @Override
                public Result apply(List<Object> results) throws Throwable {
                    User user = (User)results.get(0);
                    List<Order> orders = (List<Order>)results.get(1);
                    List<Product> products = (List<Product>)results.get(2);
                    List<Stock> stocks = (List<Stock>)results.get(3);
                    return ok(orders.size() + " order(s) for user " + user.getEmail());
                }
            })
        );
    }
}
