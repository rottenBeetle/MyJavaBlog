package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Post;
import com.rottenbeetle.myblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BlogController {



    @GetMapping("/blog")
    public String blog(Model model){

        return "blog-main";
    }
    @GetMapping("/blog/{id}")
    public String getPostById(@PathVariable int id){
        return "blog-main";
    }
}
