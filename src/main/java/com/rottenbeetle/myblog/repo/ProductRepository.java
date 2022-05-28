package com.rottenbeetle.myblog.repo;

import com.rottenbeetle.myblog.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
