package com.rottenbeetle.myblog.repo;

import com.rottenbeetle.myblog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
