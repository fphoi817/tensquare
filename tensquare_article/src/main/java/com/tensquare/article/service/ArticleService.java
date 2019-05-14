package com.tensquare.article.service;

import com.tensquare.article.dao.ArticleDao;
import com.tensquare.article.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class ArticleService {

    private final ArticleDao articleDao;
    private final RedisTemplate<String, Object> redisTemplate;
    @Autowired
    public ArticleService(ArticleDao articleDao, RedisTemplate redisTemplate){
        this.articleDao = articleDao;
        this.redisTemplate = redisTemplate;
    }

    // 根据ID查询
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article findById(String id){
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);
        if(article == null){
           article = articleDao.findById(id).get();
           redisTemplate.opsForValue().set("article_" + id, article, 60, TimeUnit.SECONDS);
        }
        return article;
    }

    // 审核
    @Transactional(propagation = Propagation.REQUIRED)
    public void examine(String id){
        articleDao.examine(id);
    }

    // 点赞
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateThumbup(String id){
        articleDao.updateThumbup(id);
    }

}
