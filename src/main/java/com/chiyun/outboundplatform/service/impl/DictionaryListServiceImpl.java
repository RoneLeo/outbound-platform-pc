package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

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
public class DictionaryListServiceImpl implements IdictionaryListService {

    @Resource
    private DictionaryListRepository dictionaryListRepository;



//    @Override
//    public List<DictionarylistEntity> findByZdid(Integer did, String zxbz) {
//        if (StringUtil.isNull(zxbz)) {
//            return dictionaryListRepository.findByZdid(did);
//        } else {
//            return dictionaryListRepository.findByZdidAndZxbz(did, zxbz);
//        }
//
//    }
//
//    @Override
//    public List<DictionarylistEntity> findByCtdm(Integer did, Integer ctdm, String zxbz) {
//        if (StringUtil.isNull(zxbz)) {
//            return dictionaryListRepository.findByCtdm(did, ctdm);
//        } else {
//            return dictionaryListRepository.findByCtdmAndZxbz(did, ctdm, zxbz);
//        }
//    }
//
//    @Override
//    public List<DictionarylistEntity> findByCtz(Integer did, String ctmc, String zxbz) {
//        if (StringUtil.isNull(zxbz)) {
//            return dictionaryListRepository.findByCtz(did, ctmc);
//        } else {
//            return dictionaryListRepository.findByCtzAndZxbz(did, ctmc, zxbz);
//        }
//    }
//
//    @Override
//    public List<DictionarylistEntity> findByCtdmAndCtz(Integer did, Integer ctdm, String ctmc, String zxbz) {
//        if (StringUtil.isNull(zxbz)) {
//            return dictionaryListRepository.findByCtdmAndCtz(did, ctdm, ctmc);
//        } else {
//            return dictionaryListRepository.findByCtdmAndCtzAndZxbz(did, ctdm, ctmc, zxbz);
//        }
//    }

    @Override
    public List<DictionarylistEntity> queryByEntity(DictionarylistEntity entity) {

        Specification querySpecifi= new Specification<DictionarylistEntity>() {
            @Override
            public Predicate toPredicate(Root<DictionarylistEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates=new ArrayList<>();
                       if(entity.getCtdm()!=0){
                           predicates.add(criteriaBuilder.equal(root.get("ctdm"),entity.getCtdm()));
                       }
                       if(StringUtil.isNotNull(entity.getCtmc())){
                           predicates.add(criteriaBuilder.equal(root.get("ctmc"),entity.getCtmc()));
                       }
                       if(StringUtil.isNotNull(entity.getZxbz())){
                           predicates.add(criteriaBuilder.equal(root.get("zxbz"),entity.getZxbz()));
                       }
                      if(entity.getZdid()!=0){
                         predicates.add(criteriaBuilder.equal(root.get("zdid"),entity.getZdid()));
                       }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
        Sort  sort=new Sort(Sort.Direction.ASC,"zxbz");
        List<DictionarylistEntity> list = dictionaryListRepository.findAll(querySpecifi,sort);
        return list;
    }


    /**
     * 新增字典项
     *
     * @param entity
     * @return
     */
    @Override
    public Map<String, Object> save(DictionarylistEntity entity) {
        System.out.println(entity.toString());
        Map<String, Object> msg = new HashMap<String, Object>();
        //查询词条是否已存在
        int cn = dictionaryListRepository.findByCtdmAndCtmcAndZdid(entity.getCtdm(), entity.getCtmc(), entity.getZdid());
        if (cn == 0) {
            DictionarylistEntity entiy1 = dictionaryListRepository.save(entity);
            if (entiy1 == null) {
                msg.put("fail", "词条新增失败");
                return msg;
            }
            entiy1.setId(entiy1.getCtdm());//ID主键和词条代码保持一致，数据库主键是词条代码
            int con = updateOne(entiy1); //完善信息后在去存入
            msg.put("success", entiy1);
        } else {
            msg.put("fail", "该词条信息已存在，请查看是否已被注销");
        }
        return msg;
    }



    @Override
    public int updateOne(DictionarylistEntity entity) {
        int cn = 0;
        DictionarylistEntity gxxx = dictionaryListRepository.save(entity);
        if (gxxx == null) {
            return cn;
        } else {
            cn = 1;
            return cn;
        }


    }


    @Override
    public int cancellationDicListById(Integer id) {
        return dictionaryListRepository.cancellationDicListById(id);
    }

    @Override
    public int unCancellationDicListById(Integer id) {
        return dictionaryListRepository.unCancellationDicListById(id);
    }

/*****************************************给其它模块提供的接口词条信息查询***********************************************/

 @Override
 public DictionarylistEntity findById(Integer id) {
    return dictionaryListRepository.findById(id);
}

@Override
public String queryCtmcByZdmcAndCtdm(String zdmc, String ctdm) {
      if(StringUtil.isNull(zdmc)||StringUtil.isNull(ctdm)){
          return  "字典名称和词条代码不能为空";
      }
    return dictionaryListRepository.queryCtmcByZdmcAndCtdm(zdmc, ctdm);
}

    @Override
    public String queryCtmcByZddmAndCtdm(String zddm, String ctdm) {
        if(StringUtil.isNull(zddm)||StringUtil.isNull(ctdm)){
            return  "字典代码和词条代码不能为空";
        }
        return dictionaryListRepository.queryCtmcByZddmAndCtdm(zddm, ctdm);
    }

    @Override
    public String queryCtdmByZddmAndCtmc(String zddm, String ctmc) {
        if(StringUtil.isNull(zddm)||StringUtil.isNull(ctmc)){
            return  "字典代码和词条名称不能为空";
        }
        return dictionaryListRepository.queryCtdmByZddmAndCtmc(zddm, ctmc);
    }

    @Override
    public String queryCtdmByZdmcAndCtmc(String zdmc, String ctmc) {
        if(StringUtil.isNull(zdmc)||StringUtil.isNull(ctmc)){
            return  "字典名称和词条名称不能为空";
        }
        return dictionaryListRepository.queryCtdmByZdmcAndCtmc(zdmc, ctmc);
    }
    /*********************便于开发调试提供的方法-在后期上线时需要删除掉****************************************************/

    @Override
    public int deleteById(Integer id) {
        return dictionaryListRepository.deleteById(id);
    }

    @Override
    public int deleteByDid(Integer did) {
        return dictionaryListRepository.deleteByDid(did);
    }
}
