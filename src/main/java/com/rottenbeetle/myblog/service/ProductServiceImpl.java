package com.rottenbeetle.myblog.service;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.domain.Product;
import com.rottenbeetle.myblog.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        Product product = null;
        if (optional.isPresent()) {
            product = optional.get();
        } else {
            throw new RuntimeException("Product not found for id :: " + id);
        }
        return product;
    }

    @Override
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findPaginated(int pageNo, int pageSize, String keyword, String category) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword != null ) {
            return productRepository.findAll(keyword.toLowerCase(), category, pageable);
        }
        if (category != null ) {
            return productRepository.findAll(keyword, category, pageable);
        }

        return productRepository.findAll(pageable);
    }
}
