package controllers;

import models.PaymentService;
import models.StockQuery;
import org.codehaus.jackson.JsonNode;
import play.data.Form;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.List;

public class Application extends Controller {

    private static final StockQuery stockQuery = new StockQuery();
    private static final PaymentService paymentService = new PaymentService();

    public static Result index() {
        return ok(index.render());
    }

    public static Result search(String query) {
        F.Promise<JsonNode> resultPromise = stockQuery.searchStock(query);
        return async(
            resultPromise.map(new F.Function<JsonNode, Result>() {
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
            })
        );
    }

    public static class Payment {
        public Integer amount;
    }
    private static Form<Payment> paymentForm = Form.form(Payment.class);

    public static Result payments() {
        Payment payment = paymentForm.bindFromRequest().get();
        F.Promise<String> stringPromise = paymentService.proceedPayments(payment.amount);
        return async(
            stringPromise.map(new F.Function<String, Result>() {
                @Override
                public Result apply(String result) throws Throwable {
                    return ok(result);
                }
            }).recover(new F.Function<Throwable, Result>() {
                @Override
                public Result apply(Throwable e) throws Throwable {
                    return internalServerError(e.getMessage());
                }
            })
        );
    }
  
}
