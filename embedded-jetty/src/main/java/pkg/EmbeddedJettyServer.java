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
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EmbeddedJettyServer {

    public static class BlockingServlet extends HttpServlet {

        protected void doPost(
                HttpServletRequest request,
                HttpServletResponse response)
                throws ServletException, IOException {

            String contentType = request.getContentType();
            if (contentType.contains("multipart")) {
                System.out.println("multipart");
                Part part = request.getPart("item_brief");
                System.out.println("part name:" + part.getName());
                byte[] bytes = new byte[(int) part.getSize()];
                part.getInputStream().read(bytes);
                String input = new String(bytes, StandardCharsets.UTF_8);
                System.out.println("input json:" + input);
            } else {
                //application/x-www-form-urlencoded
                String parameter = request.getParameter("item_brief");
                System.out.println("not multipart:\n json:");
                System.out.println(parameter);
            }


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
