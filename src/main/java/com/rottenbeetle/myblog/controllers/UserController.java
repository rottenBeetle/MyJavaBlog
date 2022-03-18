package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String listUsers(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "user-list";
    }
}
