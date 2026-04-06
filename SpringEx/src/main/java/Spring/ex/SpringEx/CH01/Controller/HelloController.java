package Spring.ex.SpringEx.CH01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // HTML 이용하지 않고 하는법
    @GetMapping("/hello")
    @ResponseBody
    public String getHello() {
        return "Hello";
    }

    // 타임리프를 이용한 방법
    @GetMapping("/HelloThymeleaf")
    public String getHelloThymeleaf(Model model) {
        model.addAttribute("data", "Spring");
        return "Hello";
    }
}
