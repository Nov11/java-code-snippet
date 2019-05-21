package pkg.old_fashion_mvc.jetty;


import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.http.HttpServlet;

public class JettyServer {
    static class Handler extends HttpServlet {

    }
    public JettyServer() {
        Server server = new Server(new QueuedThreadPool(5, 5));
        server.setStopAtShutdown(true);

        ServerConnector connector = new ServerConnector(server, 1, 1);
        connector.setPort(8080);
        connector.setAcceptQueueSize(2);
        connector.setIdleTimeout(200);
        connector.setStopTimeout(200);
        server.setConnectors(new Connector[]{connector});


    }
}
