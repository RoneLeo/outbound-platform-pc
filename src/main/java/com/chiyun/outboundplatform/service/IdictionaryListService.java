package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.DictionaryListEntity;

import java.util.List;

/**
 * Created by Linqi on 2019-03-14.
 */
public interface IdictionaryListService {
    /**
     * 通过id查询单个
     * @param id
     * @return
     */
    public DictionaryListEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<DictionaryListEntity> findAll();

    /**
     * 通过对象信息查询
     * @param entity
     * @return
     */
    //public List<DictionaryListEntity> queryByEntity(DictionaryListEntity entity);

    /**
     * 新增
     * @param entity
     * @return
     */
    public  DictionaryListEntity save(DictionaryListEntity entity);
    /**
     *  通过id删除
     */

    public int deleteById(Integer id);

    /**
     *  单个对象信息修改
     * @param entity
     * @return
     */
   // public int update(DictionaryListEntity entity);

}
