package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *   字典服务接口
 * Created by adminostrator on 2019-03-14.
 */
@Service
public interface IdictionaryService {

    /**
     *   通过id查询
     * @param id
     * @return
     */
    public DictionaryEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<DictionaryEntity> findAll();

    /**
     *  新增
     * @param entity
     * @return
     */
    public  DictionaryEntity save(DictionaryEntity entity);

    /**
     * 通过对象信息查询
     * @param entity
     * @return
     */
   // public  List<DictionaryEntity> queryByEntity(DictionaryEntity entity);

    /**
     *  通过id删除
     */
    public int deleteById(Integer id);
}
