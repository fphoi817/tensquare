package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecruitService {

    private final RecruitDao recruitDao;
    @Autowired
    public RecruitService(RecruitDao recruitDao){ this.recruitDao = recruitDao; }

    // 查询推荐职位列表
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(){
        return recruitDao.findTop4ByStateOrderByCreatetimeDesc("2");
    }

    // 查询最新职位
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(){
        return recruitDao.findTop12ByStateNotOrderByCreatetimeDesc("0");
    }
}
