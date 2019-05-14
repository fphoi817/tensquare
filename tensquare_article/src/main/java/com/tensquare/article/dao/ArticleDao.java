package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ArticleDao extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {

    // 审核
    @Modifying
    @Query("UPDATE Article art SET art.state = '1' WHERE art.id = ?1")
    void examine(String id);

    // 点赞
    @Modifying
    @Query("UPDATE Article art SET art.thumbup = art.thumbup + 1 WHERE art.id = ?1")
    void updateThumbup(String id);
}
