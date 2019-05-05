package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tools.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Label> findSearch(Label label) {
        return LABELDAO.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 1. new一个list 集合, 来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");// where labelname like "%小明%"
                    list.add(labelname);
                }

                if(!StringUtils.isEmpty(label.getState())){
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());// where state = ""
                    list.add(predicate);
                }

                // 2. new 一个数组作为最终返回值的条件
                Predicate[] predicates = new Predicate[list.size()];
                // 3. 把list直接转成数组  list.toArray(predicates);  和 predicates = list.toArray(); 是一样的
                list.toArray(predicates);
                return criteriaBuilder.and(predicates);  // where labelname like "%小明%" and state = "1"

            }
        });
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<Label> pageQuery(Label label, int page, int size) {
        // 1. 封装了分页对象
        Pageable pageable = PageRequest.of(page-1, size);
        return LABELDAO.findAll(new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 2. new一个list 集合, 来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if(!StringUtils.isEmpty(label.getLabelname())){
                    Predicate labelname = criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");// where labelname like "%小明%"
                    list.add(labelname);
                }

                if(!StringUtils.isEmpty(label.getState())){
                    Predicate predicate = criteriaBuilder.equal(root.get("state").as(String.class), label.getState());// where state = ""
                    list.add(predicate);
                }

                // 3. new 一个数组作为最终返回值的条件
                Predicate[] predicates = new Predicate[list.size()];
                // 4. 把list直接转成数组  list.toArray(predicates);  和 predicates = list.toArray(); 是一样的
                list.toArray(predicates);
                return criteriaBuilder.and(predicates);  // where labelname like "%小明%" and state = "1"

            }
        }, pageable);
    }
}
