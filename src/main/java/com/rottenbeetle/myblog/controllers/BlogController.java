package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Post;
import com.rottenbeetle.myblog.repo.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("")
    public String blog(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        Collections.reverse((List<Post>) posts);
        model.addAttribute("posts", posts);
        return "blog-main";
    }

    @GetMapping("/fillingPost")
    public String fillPost(Model model) {
        Post post = new Post();
        model.addAttribute("post", post);
        return "filling-post";
    }

    @PostMapping("/addPost")
    public String addPost(@RequestParam("title") String title, @RequestParam("fullText") String fullText,
                          @RequestParam("anons") String anons, @RequestParam long id) {
        Date dateNow = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        //regexp для лишних тегов
        String unnecessaryWord = "(?<!\\S)(?:без|это|как|так|и|в|над|к|до|не|на|но|за|то|с|ли|а|во|от|со|для|о|же|ну|вы|бы|что|кто|он|она)(?!\\S)|\\pP ";
        //Создание тэгов для поиска
        List<String> listTags = new ArrayList<>();
        String filterTitle = title.replaceAll(unnecessaryWord, "");
        String filterAnons = anons.replaceAll(unnecessaryWord, "");
        listTags.addAll(Arrays.asList(filterTitle.split(" ")));
        listTags.addAll(Arrays.asList(filterAnons.split(" ")));
        System.out.println(listTags);

        Post post = new Post(title, anons, fullText, 0, formatForDateNow.format(dateNow), listTags
                .stream().distinct()
                .collect(Collectors.toList()));
        post.setId(id);
        postRepository.save(post);
        return "redirect:/blog/";
    }


    @GetMapping("/{id}")
    public String getPostById(@PathVariable long id, Model model) {
        if (!postRepository.existsById(id)) {
            return "redirect:/blog/";
        }
        Optional<Post> postOptional = postRepository.findById(id);
        Post post = postOptional.get();

        System.out.println(post.getTags().toString());
        //Нужно доработать
        post.setViews(post.getViews() + 1);
        postRepository.save(post);

        model.addAttribute("post", post);
        return "view-post";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(Model model, @PathVariable long id) {
        Post post = postRepository.findById(id).get();
        model.addAttribute("post", post);
        return "filling-post";
    }

    @PostMapping("/deletePost/{id}")
    public String deletePost(@PathVariable long id) {
        postRepository.deleteById(id);
        return "redirect:/blog/";
    }

    @PostMapping("/filter")
    public String filterPosts(@RequestParam("filter") String filter, Model model) {
        if (filter.equals("")){
            return "redirect:/blog/";
        }
        List<String> tags = Arrays.asList(filter.split(" "));
        Set<Post> posts = new LinkedHashSet<>(postRepository.findByTagsIgnoreCaseIn(tags));
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "blog-main";
    }
}
