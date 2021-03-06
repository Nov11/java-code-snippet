package pkg.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class D2Controller {
    @ResponseBody
    @RequestMapping("/name2")
    public String name2() {
        return "name2";
    }

    @ResponseBody
    @RequestMapping("/interrupt")
    public String inter() {
        try {
            throw new InterruptedException("ll");
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        return "name2";
    }
}
