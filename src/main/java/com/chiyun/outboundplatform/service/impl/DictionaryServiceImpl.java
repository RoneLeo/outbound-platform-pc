package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.repository.DictionaryRepository;
import com.chiyun.outboundplatform.service.IdictionaryService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
    public List<DictionaryEntity> findByName(String name) {
        return dictionaryRepository.findByName(name);
    }

    @Override
    public List<DictionaryEntity> findByEng_Name(String eng_name) {
        return dictionaryRepository.findByEng_Name(eng_name);
    }

    @Override
    public List<DictionaryEntity> findAll() {

        return dictionaryRepository.findAll();

    }

    @Override
    public DictionaryEntity save(DictionaryEntity entity) {
        return dictionaryRepository.save(entity);
    }


    @Override
    public int deleteById(Integer id) {
        return dictionaryRepository.deleteById(id);
    }

    @Override
    public int updateOne(DictionaryEntity entity) {
        return dictionaryRepository.updateOne(entity);
    }

    @Override
    public int zhuXiaoOne(Integer id) {
        return dictionaryRepository.zhuXiaoOne(id);
    }


}
