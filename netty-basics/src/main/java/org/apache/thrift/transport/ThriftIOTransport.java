package org.apache.thrift.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftIOTransport {
    private static final Logger logger = LoggerFactory.getLogger(ThriftIOTransport.class);

    public static void main(String[] args) {
        TIOStreamTransport tioStreamTransport = new TIOStreamTransport();
        byte[] buffer = tioStreamTransport.getBuffer();
        logger.info("buffer : {}", buffer);
    }
}
