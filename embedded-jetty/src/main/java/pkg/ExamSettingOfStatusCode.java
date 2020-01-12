package pkg;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

import static pkg.util.ServerUtil.configLogger;

/**
 * 500 is set in HttpChannelState
 */
public class ExamSettingOfStatusCode {
    private static final Logger logger = LoggerFactory.getLogger(ExamSettingOfStatusCode.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server(8090);
        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "/");

        ServletHolder servletHolder = new ServletHolder(new ThrowingExceptionHandler());
        servletContextHandler.addServlet(servletHolder, "/throw");
        servletContextHandler.addFilter(new FilterHolder(new DumbFilter()), "/throw", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(servletContextHandler);

        configLogger(server);

        server.start();
    }

    private static class DumbFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {

        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            Exception exception = null;
            try {
                chain.doFilter(request, response);
            } catch (Exception e) {
                exception = e;
                throw e;
            } finally {
                if (exception != null) {
                    logger.error("ex: ", exception);
                }
            }
        }

        @Override
        public void destroy() {

        }
    }

    private static class ThrowingExceptionHandler extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            throw new RuntimeException("exception!");
        }
    }
}
