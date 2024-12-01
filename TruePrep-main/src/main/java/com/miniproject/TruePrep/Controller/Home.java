package com.miniproject.TruePrep.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Home {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return "Hello World";
    }
}
