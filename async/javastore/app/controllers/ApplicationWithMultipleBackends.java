package controllers;

import models.*;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class ApplicationWithMultipleBackends extends Controller {

    private static final Backends backends = new Backends();

    public static Result index(String userId) {
        F.Promise<User> user = backends.getUserById(userId);
        F.Promise<List<Order>> orders = user.flatMap(new F.Function<User, F.Promise<List<Order>>>() {
            @Override
            public F.Promise<List<Order>> apply(User user) throws Throwable {
                return backends.getOrdersForUser(user.getEmail());
            }
        });
        F.Promise<List<Product>> products = orders.flatMap(new F.Function<List<Order>, F.Promise<List<Product>>>() {
            @Override
            public F.Promise<List<Product>> apply(List<Order> orders) throws Throwable {
                return backends.getProductsForOrders(orders);
            }
        });
        F.Promise<List<Stock>> stocks = products.flatMap(new F.Function<List<Product>, F.Promise<List<Stock>>>() {
            @Override
            public F.Promise<List<Stock>> apply(List<Product> products) throws Throwable {
                return backends.getStocksForProducts(products);
            }
        });
        F.Promise<List<Object>> promises = F.Promise.sequence(user, orders, products, stocks);
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
