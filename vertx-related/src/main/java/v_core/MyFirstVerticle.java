package v_core;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class MyFirstVerticle extends AbstractVerticle {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new MyFirstVerticle());
    }

    @Override
    public void start() throws Exception {
        vertx.createHttpServer()
                .requestHandler(req ->
                        req.response().end("ok" + Thread.currentThread().getName())).listen(8080);
    }
}
