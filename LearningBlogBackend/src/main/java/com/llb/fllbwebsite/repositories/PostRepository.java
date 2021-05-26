package com.llb.fllbwebsite.repositories;

import com.llb.fllbwebsite.domain.Post;
import com.llb.fllbwebsite.domain.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Override
    Optional<Post> findById(Long postId);

    @Query("select NEW com.llb.fllbwebsite.domain.PostDto " +
            "(p.id, p.title, p.description, p.coverImage, p.categoryName, p.createdAt, p.updatedAt)  " +
            "from Post p")
    List<PostDto> getAll();

    @Query("select p from Post p where lower(p.content) like %?1% or lower(p.title) like %?1% order by p.createdAt desc ")
    List<Post> findPostByContentOrTitleIgnoreLetterCase(String searchText);

//    Post findByTitle(String postTitle);
}
