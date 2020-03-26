package pkg.asyncclient;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * execute on closed client:
 * 18:32:44.854 [main] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Connection manager is shutting down
 * 18:32:44.861 [main] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Connection manager shut down
 * Exception in thread "main" java.lang.IllegalStateException: Request cannot be executed; I/O reactor status: STOPPED
 * at org.apache.http.util.Asserts.check(Asserts.java:46)
 * at org.apache.http.impl.nio.client.CloseableHttpAsyncClientBase.ensureRunning(CloseableHttpAsyncClientBase.java:90)
 * at org.apache.http.impl.nio.client.InternalHttpAsyncClient.execute(InternalHttpAsyncClient.java:123)
 * at org.apache.http.impl.nio.client.CloseableHttpAsyncClient.execute(CloseableHttpAsyncClient.java:75)
 * at org.apache.http.impl.nio.client.CloseableHttpAsyncClient.execute(CloseableHttpAsyncClient.java:85)
 * at pkg.asyncclient.UsingClosedReactor.main(UsingClosedReactor.java:25)
 * <p>
 * client close after issuing request but before request completes. ConnectionClosedException
 * 18:42:46.728 [I/O dispatcher 1] DEBUG o.a.h.i.n.client.InternalIODispatch - http-outgoing-0 [ACTIVE] Request ready
 * 18:42:46.728 [I/O dispatcher 1] DEBUG o.a.h.i.n.c.ManagedNHttpClientConnectionImpl - http-outgoing-0 127.0.0.1:46818<->127.0.0.1:8090[ACTIVE][r:w]: Event cleared [w]
 * 18:42:51.672 [main] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Connection manager is shutting down
 * 18:42:51.672 [main] DEBUG o.a.h.i.n.c.ManagedNHttpClientConnectionImpl - http-outgoing-0 127.0.0.1:46818<->127.0.0.1:8090[ACTIVE][r:w]: Close
 * 18:42:51.673 [I/O dispatcher 1] DEBUG o.a.h.i.n.client.InternalIODispatch - http-outgoing-0 [CLOSED]: Disconnected
 * 18:42:51.674 [I/O dispatcher 1] ERROR pkg.asyncclient.UsingClosedReactor - failed :
 * org.apache.http.ConnectionClosedException: Connection closed unexpectedly
 * at org.apache.http.nio.protocol.HttpAsyncRequestExecutor.closed(HttpAsyncRequestExecutor.java:140)
 * at org.apache.http.impl.nio.client.InternalRequestExecutor.closed(InternalRequestExecutor.java:64)
 * at org.apache.http.impl.nio.client.InternalIODispatch.onClosed(InternalIODispatch.java:71)
 * at org.apache.http.impl.nio.client.InternalIODispatch.onClosed(InternalIODispatch.java:39)
 * at org.apache.http.impl.nio.reactor.AbstractIODispatch.disconnected(AbstractIODispatch.java:100)
 * at org.apache.http.impl.nio.reactor.BaseIOReactor.sessionClosed(BaseIOReactor.java:279)
 * at org.apache.http.impl.nio.reactor.AbstractIOReactor.processClosedSessions(AbstractIOReactor.java:440)
 * at org.apache.http.impl.nio.reactor.AbstractIOReactor.execute(AbstractIOReactor.java:283)
 * at org.apache.http.impl.nio.reactor.BaseIOReactor.execute(BaseIOReactor.java:104)
 * at org.apache.http.impl.nio.reactor.AbstractMultiworkerIOReactor$Worker.run(AbstractMultiworkerIOReactor.java:588)
 * at java.lang.Thread.run(Thread.java:748)
 * 18:42:51.674 [I/O dispatcher 1] DEBUG o.a.h.i.n.c.ManagedNHttpClientConnectionImpl - http-outgoing-0 [CLOSED][]: Shutdown
 * 18:42:51.674 [I/O dispatcher 1] DEBUG o.a.h.i.n.c.InternalHttpAsyncClient - [exchange: 1] connection aborted
 * 18:42:52.674 [main] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Connection manager shut down
 * 18:42:52.674 [I/O dispatcher 1] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Releasing connection: [id: http-outgoing-0][route: {}->http://localhost:8090][total kept alive: 0; route allocated: 0 of 2; total allocated: 0 of 20]
 * 18:42:52.675 [I/O dispatcher 1] DEBUG o.a.h.i.n.c.PoolingNHttpClientConnectionManager - Connection released: [id: http-outgoing-0][route: {}->http://localhost:8090][total kept alive: 0; route allocated: 0 of 2; total allocated: 0 of 20]
 * Exception in thread "main" java.util.concurrent.ExecutionException: org.apache.http.ConnectionClosedException: Connection closed unexpectedly
 * at org.apache.http.concurrent.BasicFuture.getResult(BasicFuture.java:70)
 * at org.apache.http.concurrent.BasicFuture.get(BasicFuture.java:80)
 * at org.apache.http.impl.nio.client.FutureWrapper.get(FutureWrapper.java:70)
 * at pkg.asyncclient.UsingClosedReactor.main(UsingClosedReactor.java:63)
 * Caused by: org.apache.http.ConnectionClosedException: Connection closed unexpectedly
 * at org.apache.http.nio.protocol.HttpAsyncRequestExecutor.closed(HttpAsyncRequestExecutor.java:140)
 * at org.apache.http.impl.nio.client.InternalRequestExecutor.closed(InternalRequestExecutor.java:64)
 * at org.apache.http.impl.nio.client.InternalIODispatch.onClosed(InternalIODispatch.java:71)
 * at org.apache.http.impl.nio.client.InternalIODispatch.onClosed(InternalIODispatch.java:39)
 * at org.apache.http.impl.nio.reactor.AbstractIODispatch.disconnected(AbstractIODispatch.java:100)
 * at org.apache.http.impl.nio.reactor.BaseIOReactor.sessionClosed(BaseIOReactor.java:279)
 * at org.apache.http.impl.nio.reactor.AbstractIOReactor.processClosedSessions(AbstractIOReactor.java:440)
 * at org.apache.http.impl.nio.reactor.AbstractIOReactor.execute(AbstractIOReactor.java:283)
 * at org.apache.http.impl.nio.reactor.BaseIOReactor.execute(BaseIOReactor.java:104)
 * at org.apache.http.impl.nio.reactor.AbstractMultiworkerIOReactor$Worker.run(AbstractMultiworkerIOReactor.java:588)
 * at java.lang.Thread.run(Thread.java:748)
 */
public class UsingClosedReactor {
    private static final Logger logger = LoggerFactory.getLogger(UsingClosedReactor.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        CloseableHttpAsyncClient asyncClient = HttpAsyncClientBuilder.create().build();
        asyncClient.start();

        //see exceptions if client is closed
//        asyncClient.close();
        //requesting /test
//        Future<HttpResponse> ret = asyncClient.execute(new HttpHost("localhost", 8090), new HttpGet("/test"), new FutureCallback<HttpResponse>() {
        Future<HttpResponse> ret = asyncClient.execute(new HttpHost("localhost", 8090), new HttpGet("/ss"), new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                logger.info("CALLBACK response : {}", httpResponse);
            }

            @Override
            public void failed(Exception e) {
                logger.error("failed : ", e);
            }

            @Override
            public void cancelled() {
                logger.error("cancelled ");
            }
        });

        //close
        Thread.sleep(5 * 1000);
        asyncClient.close();


        logger.info("response : {}", ret.get());
        asyncClient.close();
    }
}
