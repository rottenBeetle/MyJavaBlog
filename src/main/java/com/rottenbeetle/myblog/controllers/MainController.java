package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Product;
import com.rottenbeetle.myblog.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;



@Controller
public class MainController {


    @GetMapping("")
    public String mainPage(Model model) {
        return "redirect:/product";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
}
