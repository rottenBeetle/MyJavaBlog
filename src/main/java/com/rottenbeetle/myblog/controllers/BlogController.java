package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Post;
import com.rottenbeetle.myblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public String addPost(@RequestParam("title") String title, @RequestParam("fullText") String fullText,
                          @RequestParam("anons") String anons,Model model){
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        Post post = new Post(title,anons,fullText,0,formatForDateNow.format(dateNow));
        postRepository.save(post);
        return "redirect:/blog/";
    }


    @GetMapping("/{id}")
    public String getPostById(@PathVariable long id,Model model){
        if (!postRepository.existsById(id)){
            return "redirect:/blog/";
        }
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = new Post();
        if (postOptional.isPresent()){
             post = postOptional.get();
             //Нужно доработать
             post.setViews(post.getViews() + 1);
             postRepository.save(post);
        }
        model.addAttribute("post",post);
        return "view-post";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(Model model, @PathVariable long id){
        Post post = postRepository.findById(id).get();
        model.addAttribute("post",post);
        return "filling-post";
    }

    @GetMapping("/deletePost/{id}")
    public String deletePost(@PathVariable long id){
        postRepository.deleteById(id);
        return "redirect:/blog/";
    }


}
