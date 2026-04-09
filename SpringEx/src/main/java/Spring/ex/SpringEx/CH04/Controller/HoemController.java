package Spring.ex.SpringEx.CH04.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HoemController {

    @GetMapping("/")
    public String getHome(){
        return "Home";
    }
}
