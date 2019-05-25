package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService {

    private final SpitDao spitDao;
    private final IdWorker idWorker;
    private final MongoTemplate mongoTemplate;
    @Autowired
    public SpitService(SpitDao spitDao, IdWorker idWorker, MongoTemplate mongoTemplate){
        this.spitDao = spitDao;
        this.idWorker = idWorker;
        this.mongoTemplate = mongoTemplate;
    }

    // 增加
    @Transactional(propagation = Propagation.REQUIRED)
    public void addSer(Spit spit){
        spit.set_id(idWorker.nextId()+"");
        spit.setVisits(0);
        spit.setShare(0);
        spit.setThumbup(0);
        spit.setComment(0);
        spit.setState("1");
        if(spit.getParentid() != null && !"".equals(spit.getParentid())){
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);
            mongoTemplate.updateFirst(query, update, "spit");
        }
        spit.setPublistime(new Date());
        spitDao.save(spit);
    }

    // 修改
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateSer(String id, Spit spit){
        spit.set_id(id);
        spitDao.save(spit);
    }

    // 删除
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIdSer(String id) {
        spitDao.deleteById(id);
    }

    // 查询所有
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Spit> findAllSer() {
        return spitDao.findAll();
    }

    // 根据ID查询
    public Spit findByIdSer(String id) {
        return spitDao.findById(id).get();
    }

    // 根据上级ID 查询 并分页
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Spit> findByParentidSer(String id, int page, int size){
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(id, pageRequest);
    }

    // 修改点赞数
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateThumbupSer(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");
    }

}
