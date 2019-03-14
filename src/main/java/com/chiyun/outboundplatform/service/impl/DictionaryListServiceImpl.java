package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Linqi on 2019-03-14.
 */
@Service
public class DictionaryListServiceImpl implements IdictionaryListService{

    @Resource
    private DictionaryListRepository dictionaryListRepository;

    @Override
    public DictionaryListEntity findById(Integer id) {
        return dictionaryListRepository.findById(id);
    }

    @Override
    public List<DictionaryListEntity> findAll() {
        return dictionaryListRepository.findAll();
    }

  //  @Override
   // public List<DictionaryListEntity> queryByEntity(DictionaryListEntity entity) {
   //     return dictionaryListRepository.queryByEntity(entity);
   // }

    @Override
    public DictionaryListEntity save(DictionaryListEntity entity) {
        return dictionaryListRepository.save(entity);
    }

    @Override
    public int deleteById(Integer id) {
        return dictionaryListRepository.deleteById(id);
    }

    //@Override
   // public int update(DictionaryListEntity entity) {
   //     return dictionaryListRepository.update(entity);
   // }
}
