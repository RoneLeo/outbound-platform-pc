package com.chiyun.outboundplatform.service.impl;


import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.repository.DictionaryRepository;
import com.chiyun.outboundplatform.service.IdictionaryService;

import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Override
    public DictionaryEntity findById(Integer id) {
        return dictionaryRepository.findById(id);
    }

    @Override
    public List<DictionaryEntity> findDictByNameAndState(String name,String state) {
        if(StringUtil.isNull(state)){
            return dictionaryRepository.findByName(name);
        }else{
            return dictionaryRepository.findByNameAndState(name,state);
        }

    }

    @Override
    public List<DictionaryEntity> findDictByEng_NameAndState(String eng_name ,String state) {
        if(StringUtil.isNull(state)){
            return dictionaryRepository.findByEng_Name(eng_name);
        }else{
            return dictionaryRepository.findByEng_NameAndState(eng_name,state);
        }

    }



    @Override
    public List<DictionaryEntity> findAll(String state) {
       if(StringUtil.isNull(state)){
           return dictionaryRepository.findAll();
       }else{
           return dictionaryRepository.findAllByState(state);
       }

    }

    @Override
    public List<DictionaryEntity> findDictByNameAndEng_NameAndState(String name, String eng_name, String state) {
        if(StringUtil.isNull(state)){
            return dictionaryRepository.findDictByNameAndEng_Name(name,eng_name);
        }else{
            return dictionaryRepository.findDictByNameAndEng_NameAndState(name,eng_name,state);
        }
    }

    @Override
    public Map<String,Object>  save(DictionaryEntity entity) {
        Map<String,Object> msg=new HashMap<String,Object>();
        //查询是否已经存在
        List<DictionaryEntity> dicts=  dictionaryRepository.findDictByZdzwmAndZdywm(entity.getZdzwmc(),entity.getZdywmc());
       if(!(dicts.isEmpty())){
        msg.put("failuer","字典信息已存在");
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


}
