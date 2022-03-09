package com.rottenbeetle.myblog.repo;

import com.rottenbeetle.myblog.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String username);
}
