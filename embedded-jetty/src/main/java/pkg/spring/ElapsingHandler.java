package pkg.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ElapsingHandler extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ElapsingHandler.class);

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("get request");
        try {
            Thread.sleep(1000 * 20);
        } catch (InterruptedException e) {
            logger.error("in handler error", e);
        }
//        throw new RuntimeException("exception");
        logger.info("writing response");
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("{ \"status\": \"ok\"}");
    }
}