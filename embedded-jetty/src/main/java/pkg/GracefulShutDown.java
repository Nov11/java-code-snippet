package pkg;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GracefulShutDown {
    private static final Logger logger = LoggerFactory.getLogger(GracefulShutDown.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new ElapsingHandler());
        servletContextHandler.addServlet(servletHolder, "/ss");
        servletContextHandler.addEventListener(new ServletContextListener() {

            @Override
            public void contextInitialized(ServletContextEvent sce) {
                logger.info("init");
            }

            @Override
            public void contextDestroyed(ServletContextEvent sce) {
                logger.info("destory");
            }
        });

        StatisticsHandler statisticsHandler = new StatisticsHandler();
        statisticsHandler.setHandler(servletContextHandler);

        server.setHandler(statisticsHandler);

        server.setStopAtShutdown(true);
        server.setStopTimeout(20 * 1000);

        server.start();
    }

    public static class ElapsingHandler extends HttpServlet {

        protected void doGet(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            logger.info("get request");
            try {
                Thread.sleep(1000 * 10);
            } catch (InterruptedException e) {
                logger.error("in handler error", e);
            }
            logger.info("writing response");
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("{ \"status\": \"ok\"}");
        }
    }
}
