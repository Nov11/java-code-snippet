package pkg.v_core.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RepeatFetcher extends AbstractVerticle {
    private static final Logger logger = LoggerFactory.getLogger(RepeatFetcher.class);
    private HttpRequest<JsonObject> request;

    @Override
    public void start() {
        request = WebClient.create(vertx) // (1)
                .get(443, "icanhazdadjoke.com", "/") // (2)
                .ssl(true)  // (3)
                .putHeader("Accept", "application/json")  // (4)
                .as(BodyCodec.jsonObject()) // (5)
                .expect(ResponsePredicate.SC_OK);  // (6)

        vertx.setPeriodic(1000, id -> fetchJoke());

        logger.info("start");
    }

    private void fetchJoke() {
        request.send(asyncResult -> {
            if (asyncResult.succeeded()) {
                System.out.println(asyncResult.result().body().getString("joke")); // (7)
                System.out.println("🤣");
                System.out.println();
            }else {
                logger.info("erorr : ", asyncResult.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new RepeatFetcher());
    }
}