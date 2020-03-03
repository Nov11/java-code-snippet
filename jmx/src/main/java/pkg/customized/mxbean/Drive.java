package pkg.customized.mxbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.Queue;

public class Drive {
    private static final Logger logger = LoggerFactory.getLogger(Drive.class);

    public static void main(String[] args) throws IntrospectionException, InstanceNotFoundException, ReflectionException, MBeanException, InstanceAlreadyExistsException, NotCompliantMBeanException, InterruptedException {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName objectName = null;
        try {
            objectName = new ObjectName("site_name:type=mxbean,name=q");
        } catch (MalformedObjectNameException e) {
            logger.error("error", e);
        }

        Queue<String> q = new LinkedList<>();
        q.add("a");
        q.add("b");
        q.add("c");

        QueueSamplerMXBean queueSamplerMXBean = new QueueSampler(q);
        server.registerMBean(queueSamplerMXBean, objectName);

        MBeanInfo mBeanInfo = server.getMBeanInfo(objectName);
        logger.info("info : {}", mBeanInfo);
        Thread.sleep(1000 * 1000);
    }
}
