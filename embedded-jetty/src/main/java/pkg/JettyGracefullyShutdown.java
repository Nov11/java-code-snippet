package pkg;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JettyGracefullyShutdown {
    public static class SayHello extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            try {
                //hanging during shutdown
                Thread.sleep(1000 * 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            resp.getOutputStream().write("Hello".getBytes());
        }
    }

    public static void main(String[] args) throws Exception {
        org.eclipse.jetty.server.Server server = new Server();
        server.setStopAtShutdown(true);
        ServerConnector connector = new ServerConnector(server, 1, 1);
        connector.setPort(1700);
        connector.setIdleTimeout(60000);
        connector.setStopTimeout(3000);
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");
        servletContextHandler.addServlet(SayHello.class, "/hello");

        server.setRequestLog(new Slf4jRequestLog());

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.setHandler(servletContextHandler);
        server.setHandler(statisticsHandler);


        server.start();
    }
}
