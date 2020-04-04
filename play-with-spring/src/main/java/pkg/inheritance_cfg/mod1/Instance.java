package pkg.inheritance_cfg.mod1;

import org.springframework.stereotype.Service;

@Service
public class Instance {
    int a;

    public Instance(int a) {
        this.a = a;
    }
//    public Instance() {
//        this.a = 2;
//    }
}
