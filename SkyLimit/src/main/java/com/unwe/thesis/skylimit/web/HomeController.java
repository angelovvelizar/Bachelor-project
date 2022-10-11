package com.unwe.thesis.skylimit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/contacts")
    public String contacts(){
        return "contacts";
    }

    @PostMapping("/contacts/receive")
    public String contactsReceive(){
        return "redirect:/";
    }
}
