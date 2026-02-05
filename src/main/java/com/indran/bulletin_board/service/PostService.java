package com.indran.bulletin_board.service;

import java.util.List;

import com.indran.bulletin_board.model.Post;

public interface PostService {

    List<Post> findAllActive();
    Post findById(Long id);
    void incrementViewCount(Long id);
    void save(Post post);
    boolean update(Post post);
    boolean softDelete(Long id, String password);
    boolean verifyPassword(Long postId, String password);
    int countAllActive();
}
