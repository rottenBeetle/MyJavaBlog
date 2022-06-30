package com.rottenbeetle.myblog.service;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> optional = categoryRepository.findById(id);
        Category category = null;
        if (optional.isPresent()){
            category = optional.get();
        }else {
            throw new RuntimeException("Category not found for id :: " + id);
        }
        return category;
    }

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
