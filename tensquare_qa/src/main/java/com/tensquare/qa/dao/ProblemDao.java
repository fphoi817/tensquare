package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    // 最新 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新回复时间排序
    @Query("SELECT pro FROM Problem pro WHERE pro.id IN ( SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1) ORDER BY pro.createtime DESC ")
    Page<Problem> findNewListByLabelId(String labelId, Pageable pageable);

    // 最热 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最多回复数排序
    @Query("SELECT pro FROM Problem pro WHERE pro.id IN ( SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1 ) ORDER BY pro.reply DESC ")
    Page<Problem> findHotListByLabelId(String labelId, Pageable pageable);

    // 未解决 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新时间排序
    @Query("SELECT pro FROM Problem pro WHERE pro.id IN ( SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1 ) AND pro.reply = 0 ORDER BY pro.createtime DESC ")
    Page<Problem> findWaitListByLabelId(String labelId, Pageable pageable);
}
