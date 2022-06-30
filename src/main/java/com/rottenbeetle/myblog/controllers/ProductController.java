package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.domain.Product;
import com.rottenbeetle.myblog.service.CategoryService;
import com.rottenbeetle.myblog.service.ProductService;
import com.rottenbeetle.myblog.service.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public String mainPage(Model model, @Param("keyword") String keyword,@Param("category") String category) {
        return findPaginated(1, keyword,category, model);
    }

    @GetMapping("/fillingCourse")
    public String fillingCourse(Model model) {
        Product product = new Product();
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "add-course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@RequestParam String id, @RequestParam String title, @RequestParam String shortDescription,
                            @RequestParam String fullDescription, @RequestParam String refImage, @RequestParam int price,
                            @RequestParam("category") Long categoryId, @RequestParam String author, @RequestParam String refCourse) {

        Category category = categoryService.getCategoryById(categoryId);
        Product product = new Product(title, shortDescription, fullDescription,
                refImage, price,category,author, refCourse);
        if (!id.isEmpty())
            product.setId(Long.valueOf(id));
        productService.saveProduct(product);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String getProductById(@PathVariable long id, Model model) {
//        if (!productRepository.existsById(id)) {
//            return "redirect:/blog/";
//        }
        Product product = productService.getProductById(id);

        model.addAttribute("product", product);
        return "view-product";
    }

    @GetMapping("/editProduct/{id}")
    public String editPost(Model model, @PathVariable long id) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return "add-course";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deletePost(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "keyword", required = false) String keyword,@RequestParam(value = "category", required = false) String category,
                                Model model) {
        int pageSize = 15;
        Page<Product> page = productService.findPaginated(pageNo, pageSize, keyword,category);

        List<Product> productList = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        List<Category> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        model.addAttribute("productList", productList);
        return "index";
    }


}
