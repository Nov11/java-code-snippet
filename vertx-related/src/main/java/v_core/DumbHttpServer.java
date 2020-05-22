package v_core;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;

public class DumbHttpServer {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.createHttpServer()
                .requestHandler(new Handler<HttpServerRequest>() {
                    @Override
                    public void handle(HttpServerRequest event) {
                        event.response().end("OK");
                    }
                })
                .listen(8080)
        ;
    }
}
