package pkg;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestApiTester {
    private static final Logger logger = LoggerFactory.getLogger(RequestApiTester.class);
    private static final ObjectMapper mapper = new ObjectMapper();

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

    public static class BlockingServlet extends HttpServlet {

        protected void doPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            doWork(request, response);
        }

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            doWork(req, resp);
        }

        private void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException {
            logger.info("url {}", request.getRequestURL());
            logger.info("uri {}", request.getRequestURI());
            logger.info("query string {}", request.getQueryString());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            ABC abc = new ABC();
//            response.getWriter().println("{ \"status\": \"ok\"}");
            byte[] bytes = mapper.writeValueAsBytes(abc);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            response.getOutputStream().write(bytes);
        }
    }

    static class ABC {
        public String url = "";
    }
}
