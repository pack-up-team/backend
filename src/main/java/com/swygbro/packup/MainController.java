package com.swygbro.packup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {


    @GetMapping("/")
    public String getMethodName() {
        System.out.println("들어오냐????");
        return "login/login";
    }
    
}
