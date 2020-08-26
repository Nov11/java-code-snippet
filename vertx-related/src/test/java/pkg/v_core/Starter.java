package pkg.v_core;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.junit.Test;

public class Starter {
    @Test
    public void name() {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
    }
}