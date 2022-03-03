package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Post;
import com.rottenbeetle.myblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public String blog(Model model){
        Iterable<Post> posts = postRepository.findAll();
        Collections.reverse((List<Post>) posts);
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/fillingPost")
    public String fillPost(){
        return "filling-post";
    }

    @PostMapping("/addPost")
    public String addPost(@RequestParam("title") String title, @RequestParam("fulltext") String fullText,@RequestParam("anons") String anons,Model model){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        Post post = new Post(title,anons,fullText,0,formatForDateNow.format(dateNow));
        postRepository.save(post);
        return "redirect:/blog/";
    }


//    @GetMapping("/blog/{id}")
//    public String getPostById(@PathVariable int id){
//
//        return "blog-main";
//    }
}
