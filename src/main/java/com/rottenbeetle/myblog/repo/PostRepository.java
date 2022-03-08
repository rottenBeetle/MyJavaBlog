package com.rottenbeetle.myblog.repo;

import com.rottenbeetle.myblog.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends CrudRepository<Post,Long> {
    List<Post> findByTagsIgnoreCaseIn(Collection<String> tags);
}
