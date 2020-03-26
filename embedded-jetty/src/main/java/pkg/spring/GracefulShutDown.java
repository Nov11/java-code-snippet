package pkg.spring;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.registerShutdownHook();
        ElapsingHandler handler = context.getBean(ElapsingHandler.class);

        ServletHolder servletHolder = new ServletHolder(handler);
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

        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setLogServer(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        requestLog.setPreferProxiedForAddress(true);
        requestLog.setLogTimeZone("GMT+08:00");
        server.setRequestLog(requestLog);

        server.start();
    }
}
