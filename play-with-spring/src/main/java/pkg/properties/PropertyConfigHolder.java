package pkg.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PropertyConfigHolder {
    @Value("${value.from.file}")
    private String value;

    @Value("#{'${listOfValues}'.split(',')}")
    private List<String> list;

    @Override
    public String toString() {
        return "PropertyConfigHolder{" +
                "value='" + value + '\'' +
                ", list=" + list +
                '}';
    }
}
