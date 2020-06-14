package pkg.async_servlet;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DumbAsyncServlet {

    private static void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static class BlockingServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            delay();
            resp.setContentType("application/json");
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().println("{ \"status\": \"ok\"}");
        }
    }

    public static class AsyncServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//            AsyncContext async = req.startAsync();
//            async.start(() -> {
//                delay();
//                ServletResponse servletResponse = async.getResponse();
//                servletResponse.setContentType("application/json");
//                servletResponse.setStatus(HttpServletResponse.SC_OK);
//                servletResponse.getWriter().println("{ \"status\": \"ok\"}");
//            });


//            ServletOutputStream out = resp.getOutputStream();
//            out.setWriteListener(new WriteListener() {
//                @Override
//                public void onWritePossible() throws IOException {
//                    while (out.isReady()) {
//                        if (!content.hasRemaining()) {
//                            response.setStatus(200);
//                            async.complete();
//                            return;
//                        }
//                        out.write(content.get());
//                    }
//                }
//
//                @Override
//                public void onError(Throwable t) {
//                    getServletContext().log("Async Error", t);
//                    async.complete();
//                }
//            });
        }
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ServerConnector serverConnector = new ServerConnector(server);
        serverConnector.setPort(8080);
        server.setConnectors(new Connector[]{serverConnector});

//        ServletContextHandler handler = new ServletContextHandler(null, "/");
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(BlockingServlet.class, "/blocking");

        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setLogServer(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        requestLog.setPreferProxiedForAddress(true);
        requestLog.setLogTimeZone("GMT+08:00");
        server.setRequestLog(requestLog);

        server.setHandler(handler);

        server.start();
    }
}
