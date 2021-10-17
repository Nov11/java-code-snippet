package pkg;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.slf4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class JettyServer {
    private static final Logger logger = getLogger(JettyServer.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server(new QueuedThreadPool(6, 6));
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new Handler());
        servletContextHandler.addServlet(servletHolder, "/");

        server.setHandler(servletContextHandler);
        server.start();
    }

    public static class Handler extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            response.setContentType("test/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("{\"status\":\"OK\"}");
        }
    }
}
