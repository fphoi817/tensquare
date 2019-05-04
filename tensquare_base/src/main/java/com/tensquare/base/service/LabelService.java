package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tools.IdWorker;

import java.util.List;

@Service
public class LabelService {

    private final LabelDao LABELDAO;
    private final IdWorker IDWORKER;

    @Autowired
    public LabelService(LabelDao labelDao,
                        IdWorker idWorker) {
        this.LABELDAO = labelDao;
        this.IDWORKER = idWorker;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Label> findAll(){
        return LABELDAO.findAll();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Label findById(String id){
        return LABELDAO.findById(id).get();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Label label) {
        label.setId(IDWORKER.nextId()+"");
        LABELDAO.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(Label label){
        LABELDAO.save(label);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleById(String id){
        LABELDAO.deleteById(id);
    }

}
