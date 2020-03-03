package pkg.customized;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;

public class Drive {
    private static final Logger logger = LoggerFactory.getLogger(Drive.class);

    public static void main(String[] args) throws IntrospectionException, InstanceNotFoundException, ReflectionException, MBeanException, InstanceAlreadyExistsException, NotCompliantMBeanException, InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("site_name:type=basic,name=game");
        } catch (MalformedObjectNameException e) {
            logger.error("error", e);
        }
        server.createMBean("pkg.customized.Game", objectName);

        MBeanInfo mBeanInfo = server.getMBeanInfo(objectName);
        logger.info("info : {}", mBeanInfo);
        Thread.sleep(1000 * 1000);
    }
}
