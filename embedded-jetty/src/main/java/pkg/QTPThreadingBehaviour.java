package pkg;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pkg.util.StatsdClient;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class QTPThreadingBehaviour {
    private static final Logger logger = LoggerFactory.getLogger(QTPThreadingBehaviour.class);
    private QueuedThreadPool queuedThreadPool = new QueuedThreadPool(10, 10);

    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private Server server;

    public void start() throws Exception {
        server = new Server(queuedThreadPool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(3456);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new LimitThreadPoolServer.Handler());
        servletContextHandler.addServlet(servletHolder, "/test");

        server.setHandler(servletContextHandler);
        server.start();

        work();
    }

    public void join() throws InterruptedException {
        server.join();
    }

    public static void main(String[] args) throws Exception {
        QTPThreadingBehaviour qtpThreadingBehaviour = new QTPThreadingBehaviour();
        qtpThreadingBehaviour.start();

        qtpThreadingBehaviour.join();
    }

    private void work() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            StatsdClient.STATS_D_CLIENT.count("total", queuedThreadPool.getThreads());
            StatsdClient.STATS_D_CLIENT.count("active", queuedThreadPool.getBusyThreads());
            StatsdClient.STATS_D_CLIENT.count("idle", queuedThreadPool.getIdleThreads());
            StatsdClient.STATS_D_CLIENT.count("jobQueue", queuedThreadPool.getQueueSize());
        }, 1, 1, TimeUnit.SECONDS);
    }
}
