package pkg;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class LimitThreadPoolServer {
    private static final Logger logger = LoggerFactory.getLogger(LimitThreadPoolServer.class);

    static class Handler extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("request received");
            try {
                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.getWriter().println("lalala");
        }
    }

    public static void main(String[] args) throws Exception {
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2);
        MyQueuedThreadPool pool = new MyQueuedThreadPool(4, 4, 10000, blockingQueue);
        Server server = new Server(pool);
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(3456);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new Handler());
        servletContextHandler.addServlet(servletHolder, "/test");

        server.setHandler(servletContextHandler);
        server.start();
    }
}
