package pkg.inheritance_cfg.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DHolder {
    @Lazy
    @Autowired
    Instance instance;

    public Instance getInstance() {
        return instance;
    }
}
