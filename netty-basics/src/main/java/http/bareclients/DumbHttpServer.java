package http.bareclients;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DumbHttpServer {
    private static final Logger logger = LoggerFactory.getLogger(DumbHttpServer.class);

    static class Handler extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            logger.info("do get");
            resp.getWriter().println("done");
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");
        servletContextHandler.addServlet(Handler.class, "/ss");

        server.setHandler(servletContextHandler);
        server.start();
    }
}
