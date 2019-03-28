package com.chiyun.outboundplatform.service.impl;


import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.repository.DictionaryRepository;
import com.chiyun.outboundplatform.service.IdictionaryService;

import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqi on 2019-03-14.
 */
@Service

public class DictionaryServiceImpl implements IdictionaryService {

    @Resource
    private DictionaryRepository dictionaryRepository;

    @Resource
    private DictionaryListRepository dictionaryListRepository;

    @Override
    public DictionaryEntity findById(Integer id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    public List<DictionaryEntity> findDictByNameAndZxbz(String zdmc,String zxbz) {
        if(StringUtil.isNull(zxbz)){
            return dictionaryRepository.findByZdmc(zdmc);
        }else{
            return dictionaryRepository.findByZdmcAndZxbz(zdmc,zxbz);
        }

    }

    @Override
    public List<DictionaryEntity> findDictByEng_NameAndZxbz(String zddm ,String zxbz) {
        if(StringUtil.isNull(zxbz)){
            return dictionaryRepository.findByZddm(zddm);
        }else{
            return dictionaryRepository.findByZddmAndZxbz(zddm,zxbz);
        }

    }



    @Override
    public List<DictionaryEntity> findAll(String zxbz) {
       if(StringUtil.isNull(zxbz)){
           return dictionaryRepository.findAll();
       }else{
           return dictionaryRepository.findAllByZxbz(zxbz);
       }

    }

    @Override
    public List<DictionaryEntity>  findByZdmcAndZddmAndZxbz(String zdmc, String zddm, String zxbz) {
        if(StringUtil.isNull(zxbz)){
            return dictionaryRepository. findByZdmcAndZddm(zdmc,zddm);
        }else{
            return dictionaryRepository. findByZdmcAndZddmAndZxbz(zdmc,zddm,zxbz);
        }
    }

    @Override
    public Map<String,Object>  save(DictionaryEntity entity) {
        Map<String,Object> msg=new HashMap<String,Object>();
        //查询是否已经存在
        List<DictionaryEntity> dicts=  dictionaryRepository.findDictByZdmcAndZddm(entity.getZdmc(),entity.getZddm());
       if(!(dicts.isEmpty())){
        msg.put("failuer","字典名称或则代码已经被使用");
           return msg;
       }
         entity.setZxbz("0");//默认为未注销状态
        DictionaryEntity saveEntity=dictionaryRepository.save(entity);
         if(!(saveEntity==null)){
             msg.put("success",saveEntity);
         }else {
             msg.put("failuer","字典保存失败");
         }
        return msg;
    }


    @Override
    public int deleteById(Integer id) {
        return dictionaryRepository.deleteById(id);
    }



    @Override
    public int update(DictionaryEntity entity) {
        //更新操作，要求主键不能为 ‘0’
        if(entity.getId()==0){
            return 0;
        }

        DictionaryEntity  entity1= dictionaryRepository.save(entity);
        dictionaryListRepository.updateForDictnameAndDictengname(entity1.getZdmc(),entity1.getZddm(),entity1.getId());
          if(!(entity1==null)){
              return  1;
          }

        return 0;
    }
    @Override
    public int  cancellationById(Integer id) {
        return dictionaryRepository.cancellationById(id);
    }

    @Override
    public int unCancellationById(Integer id) {
        return dictionaryRepository.unCancellationById(id);
    }

    @Override
    public Boolean existsById(Integer id) {
        return dictionaryRepository.existsById(id);
    }

    @Override
    public List<DictionaryEntity> queryByEntity(DictionaryEntity entity) {

        Specification querySpecifi= new Specification<DictionaryEntity>() {

            @Override
            public Predicate toPredicate(Root<DictionaryEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                List<Predicate> predicates=new ArrayList<>();
                if(StringUtil.isNotNull(entity.getZdmc())){
                    predicates.add(cb.equal(root.get("zdmc"),entity.getZdmc()));
                }
                if(StringUtil.isNotNull(entity.getZddm())){
                    predicates.add(cb.equal(root.get("zddm"),entity.getZddm()));
                }
                if(StringUtil.isNotNull(entity.getZxbz())){
                    predicates.add(cb.equal(root.get("zxbz"),entity.getZxbz()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort sort=new Sort(Sort.Direction.ASC,"zxbz");
        List<DictionaryEntity> list= dictionaryRepository.findAll(querySpecifi,sort);
        return list;
    }


}
