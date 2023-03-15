package hanseul.simpleBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/posts")
    public String getPostTitles() {
        return "posts";
    }
}