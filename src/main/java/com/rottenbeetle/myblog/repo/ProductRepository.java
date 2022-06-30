package com.rottenbeetle.myblog.repo;

import com.rottenbeetle.myblog.domain.Category;
import com.rottenbeetle.myblog.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("SELECT s FROM Product s WHERE lower(CONCAT(s.title, ' ', s.shortDescription," +
            " ' ', s.fullDescription)) LIKE %?1% OR s.category.name LIKE %?2%")
    Page<Product> findAll(String keyword, String category, Pageable pageable);
}
