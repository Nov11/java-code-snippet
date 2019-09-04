package pkg.mvc;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Slf4jRequestLog;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MVCServer {
    public static void main(String[] args) throws Exception {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(MVCConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
//        context.refresh();

        ServletContextHandler servletContextHandler = new ServletContextHandler(null, "");
        servletContextHandler.addServlet(new ServletHolder(dispatcherServlet), "/");

//        servletContextHandler.addEventListener(new ContextLoaderListener());
//        servletContextHandler.setInitParameter("contextClass", org.springframework.web.context.support.AnnotationConfigWebApplicationContext.class.getName());
//        servletContextHandler.setInitParameter("contextConfigLocation", getConfigLocation());

        Server server = new Server(8090);
        server.setStopAtShutdown(true);
        server.setHandler(servletContextHandler);

        Slf4jRequestLog requestLog = new Slf4jRequestLog();
        requestLog.setLogServer(true);
        requestLog.setExtended(true);
        requestLog.setLogLatency(true);
        requestLog.setPreferProxiedForAddress(true);
        requestLog.setLogTimeZone("GMT+08:00");
        server.setRequestLog(requestLog);

        server.start();

        server.join();
    }
}
