package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Linqi on 2019-03-14.
 */
public interface IdictionaryListService {
    /**
     * 通过id查询单个
     * @param id
     * @return
     */
     DictionarylistEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
     List<DictionarylistEntity> findAll(String state);



    /**
     * 新增
     * @param entity
     * @return
     */
      Map<String,Object> save(DictionarylistEntity entity);
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
     int updateOne(DictionarylistEntity entity);

    /**
     *  通过外键批量注销字典项
     * @param did
     * @return
     */
     int zhuXiaoByDid(Integer did);

     int   zhuXiaoOne(Integer id);

    int updateZdywmAndZdzwnAndDid(String zdywm,String zdzwm ,Integer Ndid,Integer Odid);

    List<DictionarylistEntity> findBydid(Integer did);

    List<DictionarylistEntity>  findByZdzwm(String zdzwm);

    List<DictionarylistEntity>  findByZdywm(String zdywm);

}
