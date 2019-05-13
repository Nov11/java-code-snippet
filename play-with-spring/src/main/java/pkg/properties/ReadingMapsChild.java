package pkg.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReadingMapsChild {
    @Value("#{${mapProperty}}")
    Map<String, String> map;

    @Value("#{${mapDouble}}")
    Map<String, Double> dmap;
}
