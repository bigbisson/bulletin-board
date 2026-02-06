package com.indran.bulletin_board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.indran.bulletin_board.model.Post;

@Mapper
public interface PostMapper {

    List<Post> findAllActive();

    Post findById(@Param("id") Long id);

    void incrementViewCount(@Param("id") Long id);
    
    void insert(Post post);

    void update(Post post);
    
    void softDelete(@Param("id") Long id);
    
    String findPasswordById(@Param("id") Long id);
    
    int countAllActive();
}
