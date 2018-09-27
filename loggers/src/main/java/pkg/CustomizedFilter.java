package pkg;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;

@Plugin(name = "CustomizedFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public class CustomizedFilter extends AbstractFilter {
    private final Level level = Level.ALL;

    protected CustomizedFilter(Result onMatch, Result onMismatch) {
        super(onMatch, onMismatch);
    }

    @PluginFactory
    public static CustomizedFilter make() {
        System.out.println("myFilter init");
        CustomizedFilter customizedFilter = new CustomizedFilter(Result.DENY, Result.NEUTRAL);
        customizedFilter.setStarted();
        return customizedFilter;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        System.out.println("kkkk");
        return filter(logger, level, marker, (Object) msg, t);
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        System.out.println("hhh");
        if (t instanceof NullPointerException) {
            return Result.DENY;
        }
        return Result.NEUTRAL;
    }

    public Result filter(Logger logger, Level level, Marker marker, String msg, Object[] params) {
        System.out.println(">>>");
        return Result.NEUTRAL;
    }

    Set<Class> hashSet;
    {
        hashSet = new HashSet<>();
        hashSet.add(NullPointerException.class);
    }

    @Override
    public Result filter(LogEvent event) {
        System.out.println("here!");
        Throwable throwable = event.getThrown();
        if (throwable != null && hashSet.contains(throwable.getClass())) {
            return Result.DENY;
        }
        return Result.ACCEPT;
    }
    static org.slf4j.Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
            System.out.println(url.getFile());
        }
        NullPointerException exception = new NullPointerException();
        logger.warn("ee :{}", exception);
    }


}
