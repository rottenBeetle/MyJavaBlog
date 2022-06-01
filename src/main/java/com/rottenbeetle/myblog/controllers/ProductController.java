package com.rottenbeetle.myblog.controllers;

import com.rottenbeetle.myblog.domain.Product;
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

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }


    @GetMapping("")
    public String mainPage(Model model,@Param("keyword") String keyword) {
        return findPaginated(1,keyword,model);
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
        model.addAttribute("product", product);
        return "add-course";
    }

    @PostMapping("/deleteProduct/{id}")
    public String deletePost(@PathVariable long id) {
        productService.deleteProduct(id);
        return "redirect:/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                Model model){
        int pageSize = 15;
        Page<Product> page = productService.findPaginated(pageNo,pageSize,keyword);

        List<Product> productList = page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());

        model.addAttribute("keyword",keyword);

        model.addAttribute("productList", productList);
        return "index";
    }
}
