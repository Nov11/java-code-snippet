package pkg.wiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class A {
    @Autowired
    A a;

    public String func() {
        return "func";
    }

    public String wrapper() {
        return a.func();
    }
}
