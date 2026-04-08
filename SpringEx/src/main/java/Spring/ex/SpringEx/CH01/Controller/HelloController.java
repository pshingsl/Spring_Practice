package Spring.ex.SpringEx.CH01.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    // HTML 이용하지 않고 하는법
    @GetMapping("/hello")
    @ResponseBody
    public String getHello() {
        return "Hello Spring";
    }

    // 타임리프를 이용한 방법
    @GetMapping("/HelloThymeleaf")
    public String getHelloThymeleaf(Model model) {
        model.addAttribute("data", "Spring");
        return "Hello";
    }

    // 파라미터 받는방법
    // 서버주소?@RequestParam(변수명)=변수값
    // 지금 서버 주소는 hello-mvc?name=값
    // String name은 위에 있는 값을 저장하는 공간
    @GetMapping("hello-mvc")
    public String getParameter(@RequestParam(value = "name") String name, Model model){
        model.addAttribute("name", name);
        return "Hello-temp";
    }

    // Json 받는방법
    @GetMapping("hello-json")
    @ResponseBody
    public Hello getJson(@RequestParam("name") String name){
       Hello hello = new Hello();
       hello.setName(name);
       return hello;
    }
    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
