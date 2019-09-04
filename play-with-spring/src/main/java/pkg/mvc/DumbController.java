package pkg.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DumbController {
    @ResponseBody
    @RequestMapping("/name")
    public String name() {
        return "name";
    }
}
