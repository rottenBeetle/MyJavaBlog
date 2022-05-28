package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Product;
import com.rottenbeetle.myblog.repo.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public String mainPage(Model model) {
        List<Product> productList = productRepository.findAll();
        model.addAttribute("productList", productList);
        return "index";
    }

    @GetMapping("/fillingCourse")
    public String fillingCourse(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam String id, @RequestParam String title, @RequestParam String shortDescription,
                            @RequestParam String fullDescription, @RequestParam String refImage, @RequestParam int price,
                            @RequestParam String category, @RequestParam String refCourse) {

        Product product = new Product(title, shortDescription, fullDescription,
                refImage, price, category, refCourse);
        if (!id.isEmpty())
            product.setId(Long.valueOf(id));
        productRepository.save(product);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable long id, Model model) {
        if (!productRepository.existsById(id)) {
            return "redirect:/blog/";
        }
        Optional<Product> productOptional = productRepository.findById(id);
        Product product = productOptional.get();

        model.addAttribute("product", product);
        return "view-product";
    }

    @GetMapping("/editProduct/{id}")
    public String editPost(Model model, @PathVariable long id) {
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "add-course";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deletePost(@PathVariable long id) {
        productRepository.deleteById(id);
        return "redirect:/";
    }
}
