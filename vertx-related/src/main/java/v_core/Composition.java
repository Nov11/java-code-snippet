package v_core;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;


public class Composition {
    private static final Logger logger = getLogger(Composition.class);

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        FileSystem fs = vertx.fileSystem();

        Future<Void> fut1 = Future.future(promise -> fs.createFile("/foo", promise));

        Future<Void> startFuture = fut1
                .compose(v -> {
                    // When the file is created (fut1), execute this:
                    return Future.<Void>future(promise -> fs.writeFile("/foo", Buffer.buffer(), promise));
                })
                .compose(v -> {
                    // When the file is written (fut2), execute this:
                    return Future.future(promise -> fs.move("/foo", "/bar", promise));
                });
    }
}
