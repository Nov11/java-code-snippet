package pkg.v_core;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public class WorkerVerticle {
    private static final Logger logger = getLogger(WorkerVerticle.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
        vertx.executeBlocking(promise -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            promise.complete("done!");
            logger.info("thread ");
        }, result -> {
            logger.info("result : {}", result);
        });

        logger.info("main thread");

    }
}
