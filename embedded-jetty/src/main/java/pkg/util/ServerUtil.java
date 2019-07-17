package pkg.util;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.Servlet;

public class ServerUtil {
    public static Server makeServer(int port, Class<? extends Servlet> servletClass) {
        Server server = new Server();

        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.setConnectors(new Connector[]{connector});

        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");
        servletContextHandler.addServlet(servletClass, "/test");
        server.setHandler(servletContextHandler);

        return server;
    }

    public static Server makeServer(Class<? extends Servlet> servletClass) {
        return makeServer(1026, servletClass);
    }
}
