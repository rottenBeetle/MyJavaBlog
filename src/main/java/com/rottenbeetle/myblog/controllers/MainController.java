package com.rottenbeetle.myblog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping({"/",""})
    public String mainPage(){
        return "index";
    }
    @RequestMapping("/about")
    public String about(){
        return "about";
    }
}
