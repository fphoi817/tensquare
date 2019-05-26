package com.tensquare.qa.service;

import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import com.tensquare.utils.IdWorker;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class ProblemService {

    private final ProblemDao problemDao;
    private HttpServletRequest request;
    private IdWorker idWorker;
    @Autowired
    public ProblemService(ProblemDao problemDao, HttpServletRequest request, IdWorker idWorker){
        this.problemDao = problemDao;
        this.request = request;
        this.idWorker = idWorker;
    }

    // 最新 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新回复时间排序
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Problem> findNewListByLabelId(String labelId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findNewListByLabelId(labelId, pageRequest);
    }

    // 最热 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最多回复数排序
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Problem> findHotListByLabelId(String lableId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findHotListByLabelId(lableId, pageRequest);
    }

    // 未解决 根据标签ID 查询多个问题的列表  标签和问题是多对多关系  按最新时间排序
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Problem> findWaitListByLabelId(String labelId, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return problemDao.findWaitListByLabelId(labelId, pageRequest);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Problem problem) {
        isAuthorization();
        problem.setId(String.valueOf(idWorker.nextId()));
        problemDao.save(problem);
    }

    private void isAuthorization() {
        Claims claims = (Claims) request.getAttribute("normal_claims");
        if(claims == null){
            throw new RuntimeException("请登录");
        }
    }
}
