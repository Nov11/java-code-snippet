package pkg.produceException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProduceIdleTimeoutException {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        connector.setIdleTimeout(100);
        server.setConnectors(new ServerConnector[]{connector});

        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");
        servletContextHandler.addServlet(DumbHandler.class, "/ss");
        server.setHandler(servletContextHandler);

        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setLogServer(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        requestLog.setPreferProxiedForAddress(true);
        requestLog.setLogTimeZone("GMT+08:00");
        server.setRequestLog(new Slf4jRequestLog());

        server.start();
    }

    public static class DumbHandler extends HttpServlet {
        private static final Logger logger = LoggerFactory.getLogger(DumbHandler.class);

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("enter do get method");
            try {
                resp.getWriter().println("1OK");
                Thread.sleep(1000 * 2);
                resp.getWriter().println("2OK");
            } catch (Exception e) {
                logger.error("ex", e);
            }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("enter do post method");
            int length = req.getContentLength();
            byte[] b = new byte[length];
            req.getInputStream().read(b);
            String s = new String(b);
            logger.info("body : {}", s);
        }
    }
}
