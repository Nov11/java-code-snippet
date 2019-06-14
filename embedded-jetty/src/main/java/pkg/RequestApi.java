package pkg;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestApi {

    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RequestApi.class);

    public static class BlockingServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            logger.info("uri {}", request.getRequestURI());
            logger.info("url {}", request.getRequestURL());
            logger.info("query string {}", request.getQueryString());
            logger.info("servlet path {}", request.getServletPath());

            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("{ \"status\": \"ok\"}");
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8090);
        server.setConnectors(new Connector[]{connector});
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new BlockingServlet());
        // multipart config. '/tmp' location doesn't matter
        servletHolder.getRegistration().setMultipartConfig(new MultipartConfigElement("/tmp"));
        servletContextHandler.addServlet(servletHolder, "/ss");

        server.setHandler(servletContextHandler);
        server.start();
    }
}
