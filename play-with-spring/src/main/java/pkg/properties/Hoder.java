package pkg.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Hoder {

    @Autowired
    EmptyObj emptyObj;

    private String value;

    @Value("#{'${listOfValues}'.split(',')}")
    private List<String> list;

    public Hoder(@Value("${value.from.file}")

                         String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Hoder{" +
                "value='" + value + '\'' +
                ", list=" + list +
                '}';
    }
}
