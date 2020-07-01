package pkg.v_core;


import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpServer;

public class RxVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestStream().toObservable().subscribe(req -> req.response().end("hello from " + Thread.currentThread().getName()));
        httpServer.rxListen(8080).subscribe();
    }
}
