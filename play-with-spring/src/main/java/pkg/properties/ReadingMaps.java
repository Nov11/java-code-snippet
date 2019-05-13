package pkg.properties;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReadingMaps implements InitializingBean {
    @Value("#{${mapProperty}}")
    Map<String, String> map;

    @Value("#{${mapDouble}}")
    Map<String, Double> dmap;

    @Autowired
    Environment environment;

    @Override
    public void afterPropertiesSet() throws Exception {
        String prefix = this.getClass().getSimpleName();
        limit = environment.getProperty(prefix + ".limit", Integer.TYPE, 10);
        String mapString = environment.getProperty(prefix + ".paramMap", String.class, "{}");
        ObjectMapper objectMapper = new ObjectMapper();
        hashMap = objectMapper.readValue(mapString, new TypeReference<Map<String, String>>() {
        });

        sss = environment.getProperty(prefix + ".sss", String.class, null);
    }

//    @Value("#{this.getClass().getSimpleName()}")
//    String simpleName;

    int limit;

    Map<String, String> hashMap;

    String sss;
}
