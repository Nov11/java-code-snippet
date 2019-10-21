package pkg.inheritance_cfg.mod1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Holder {
    @Autowired
    Instance instance;
}
