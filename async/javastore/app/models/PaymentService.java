package models;

import play.libs.F;
import play.libs.Json;
import play.libs.WS;

import java.util.HashMap;
import java.util.Map;

public class PaymentService {

    public F.Promise<String> proceedPayments(Integer amount) {
        Map<String, Object> jsonRequest = new HashMap<String, Object>();
        jsonRequest.put("amount", amount);
        F.Promise<WS.Response> responsePromise = WS.url("http://localhost:9002/payments").post(Json.toJson(jsonRequest));
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
