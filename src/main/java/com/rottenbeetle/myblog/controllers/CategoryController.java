package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/save")
    public String saveCategory(@RequestParam("category") String name){
        Category category = new Category(name);
        categoryService.saveCategory(category);
        return "redirect:/product/fillingCourse";
    }
}
