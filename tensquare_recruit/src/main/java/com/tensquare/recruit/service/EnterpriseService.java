package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.EnterpriseDao;
import com.tensquare.recruit.pojo.Enterprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EnterpriseService {

    private final EnterpriseDao enterpriseDao;

    @Autowired
    public EnterpriseService(EnterpriseDao enterpriseDao){ this.enterpriseDao = enterpriseDao; }

    // 热门企业列表
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Enterprise> findByIshot(){
        return enterpriseDao.findByIshot("1");
    }

}
