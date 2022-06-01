package com.rottenbeetle.myblog.service;

import com.rottenbeetle.myblog.domain.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void saveProduct(Product product);
    void deleteProduct(Long id);
    Page<Product> findPaginated(int pageNo, int pageSize, String keyword);
}
