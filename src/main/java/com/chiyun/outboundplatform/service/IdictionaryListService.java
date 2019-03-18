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
     DictionaryListEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
     List<DictionaryListEntity> findAll();



    /**
     * 新增
     * @param entity
     * @return
     */
      DictionaryListEntity save(DictionaryListEntity entity);
    /**
     *  通过id删除
     */

     int deleteById(Integer id);

    /**
     * 通过did 外键 批量删除
     * @param did
     * @return
     */
     int deleteByDid(Integer did);


    /**
     * 根据字典英文名和字典项代号查询字典项的值
     * @param zdywm
     * @param key
     * @return
     */
      String querDictListByZdywmAndKey(String zdywm, String key);

    /**
     * 根据字典中文名和字典项代号查询字典项的值
     * @param zdzwm
     * @param key
     * @return
     */
      String  querDictListByZdzwmAndKey(String zdzwm, String key);

    /**
     *  单个对象信息修改
     * @param entity
     * @return
     */
     int updateOne(DictionaryListEntity entity);

    /**
     *  通过外键批量注销字典项
     * @param did
     * @return
     */
     int zhuXiaoByDid(Integer did);

     int   zhuXiaoOne(Integer id);

    int updateZdywmAndZdzwnAndDid(String zdywm,String zdzwm ,Integer Ndid,Integer Odid);

    List<DictionaryListEntity> findBydid(Integer did);

    List<DictionaryListEntity>  findByZdzwm(String zdzwm);

    List<DictionaryListEntity>  findByZdywm(String zdywm);

}
