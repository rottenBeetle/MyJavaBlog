package com.rottenbeetle.myblog.service;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.domain.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategory();
    Category getCategoryById(Long id);
    void saveCategory(Category category);
    void deleteCategory(Long id);
}
