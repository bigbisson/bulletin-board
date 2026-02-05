package com.indran.bulletin_board.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.indran.bulletin_board.mapper.PostMapper;
import com.indran.bulletin_board.model.Post;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {
    
    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
    private final PostMapper postMapper;

    public PostServiceImpl(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Override
    public List<Post> findAllActive() {
        logger.debug("Finding all active posts");
        return postMapper.findAllActive();
    }

    @Override
    public Post findById(Long id) {
        logger.debug("Finding post by id: {}");
        return postMapper.findById(id);
    }

    @Override
    @Transactional
    public void incrementViewCount(Long id) {
        logger.debug("Incrementing view coint for post: {}", id);
        postMapper.incrementViewCount(id);
    }

    @Override
    @Transactional
    public void save(Post post) {
        logger.info("Saving new post: {}", post.getTitle());
        postMapper.insert(post);
    }

    @Override
    @Transactional
    public boolean update(Post post) {
        logger.info("Updating post: {}", post.getId());

         if (!verifyPassword(post.getId(), post.getPassword())) {
            logger.warn("Password verification failed for post: {}", post.getId());
            return false;
        }
        
        postMapper.update(post);
        return true;
    }

    @Override
    @Transactional
    public boolean softDelete(Long id, String password) {
        logger.info("Soft deleting post: {}", id);
        
        if (!verifyPassword(id, password)) {
            logger.warn("Password verification failed for delete on post: {}", id);
            return false;
        }
        
        postMapper.softDelete(id);
        return true;
    }

    @Override
    public boolean verifyPassword(Long postId, String password) {
        logger.debug("Verifying password for post: {}", postId);
        String storedPassword = postMapper.findPasswordById(postId);
        return storedPassword != null && storedPassword.equals(password);
    }
    
    @Override
    public int countAllActive() {
        return postMapper.countAllActive();
    }
}