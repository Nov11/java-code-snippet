package pkg.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

public class BinaryContentHandler extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(BinaryContentHandler.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("request received");
        Enumeration<String> enumeration = req.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String headerName = enumeration.nextElement();
            String value = req.getHeader(headerName);
            logger.info("head : {} : {}", headerName, value);
        }
        InputStream in = req.getInputStream();
        OutputStream out = resp.getOutputStream();
        int ret = -1;
        while ((ret = in.read()) != -1) {
            logger.info("{}", ret);
            out.write(ret);
        }

        resp.setContentType("application/octet-stream");
        logger.info("drained input stream");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
