package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.PaymentService;
import models.StockQuery;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

import static play.libs.F.Promise;

public class Application extends Controller {

    private static final StockQuery stockQuery = new StockQuery();
    private static final PaymentService paymentService = new PaymentService();

    public static Result index() {
        return ok(index.render());
    }

    public static Promise<Result> search(String query) {
        Promise<JsonNode> resultPromise = stockQuery.searchStock(query);
        return resultPromise.map(new F.Function<JsonNode, Result>() {
            @Override
            public Result apply(JsonNode jsonNode) throws Throwable {
                boolean first = true;
                String result = "Found results: ";
                List<String> descriptions = jsonNode.findValuesAsText("description");
                for (String description : descriptions) {
                    if (!first) {
                        result += ", ";
                    }
                    result += description;
                    first = false;
                }
                result += ".\n";
                return ok(result);
            }
        }).recover(new F.Function<Throwable, Result>() {
            @Override
            public Result apply(Throwable e) throws Throwable {
                return internalServerError(e.getMessage());
            }
        });
    }

    public static class Payment {
        public Integer amount;
    }
    private static Form<Payment> paymentForm = Form.form(Payment.class);

    public static Promise<Result> payments() {
        Payment payment = paymentForm.bindFromRequest().get();
        Promise<String> stringPromise = paymentService.proceedPayments(payment.amount);
        return stringPromise.map(new F.Function<String, Result>() {
            @Override
            public Result apply(String result) throws Throwable {
                return ok(result);
            }
        }).recover(new F.Function<Throwable, Result>() {
            @Override
            public Result apply(Throwable e) throws Throwable {
                return internalServerError(e.getMessage());
            }
        });
    }
  
}
