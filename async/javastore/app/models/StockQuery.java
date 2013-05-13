package models;

import org.codehaus.jackson.JsonNode;
import play.libs.F;
import play.libs.WS;

import static play.libs.F.Promise;

public class StockQuery {

    public Promise<JsonNode> searchStock(String query) {
        Promise<WS.Response> responsePromise = WS.url("http://localhost:9001/search").setQueryParameter("query", query).get();
        return responsePromise.map(new F.Function<WS.Response, JsonNode>() {
            @Override
            public JsonNode apply(WS.Response response) throws Throwable {
                if (response.getStatus() == 200) {
                    JsonNode jsonNode = response.asJson();
                    return jsonNode.findPath("results");
                } else {
                    throw new Exception("Error calling search service.\nResponse status " + response.getStatus() + "\n");
                }
            }
        });
    }
}
