package com.tensquare.gathering.service;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GatheringService {

    private final GatheringDao gatheringDao;
    @Autowired
    public GatheringService(GatheringDao gatheringDao){
        this.gatheringDao = gatheringDao;
    }


    // 根据 ID 查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Cacheable(value = "gathering", key = "#id")
    public Gathering findByIdSer(String id){
        return gatheringDao.findById(id).get();
    }
}
