package models;

import play.libs.F;
import play.libs.Json;
import play.libs.WS;

import java.util.HashMap;
import java.util.Map;

import static play.libs.F.Promise;

public class PaymentService {

    public Promise<String> proceedPayments(Integer amount) {
        Map<String, Object> jsonRequest = new HashMap<String, Object>();
        jsonRequest.put("amount", amount);
        Promise<WS.Response> responsePromise = WS.url("http://localhost:9002/payments").post(Json.toJson(jsonRequest));
        return responsePromise.map(new F.Function<WS.Response, String>() {
            @Override
            public String apply(WS.Response response) throws Throwable {
                if (response.getStatus() == 200) {
                    return "Payment processed.\n";
                } else {
                    throw new Exception("Error calling payment service.\nResponse status " + response.getStatus() + "\n");
                }
            }
        });
    }
}
