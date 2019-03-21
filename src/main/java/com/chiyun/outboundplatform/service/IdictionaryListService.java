package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;

import java.util.List;
import java.util.Map;

/**
 * Created by Linqi on 2019-03-14.
 */
public interface IdictionaryListService {




    /**
     * 新增
     * @param entity
     * @return
     */
      Map<String,Object> save(DictionarylistEntity entity);

      /********************************************上线前需要注释掉的代码*******************************************************/
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

    /***************************************************************************************************/


    /**
     *  单个对象信息修改
     * @param entity
     * @return
     */
     int updateOne(DictionarylistEntity entity);


    /**
     * 通过 id注销
     * @param id
     * @return
     */
     int   cancellationDicListById(Integer id);

    int   unCancellationDicListById(Integer id);


    List<DictionarylistEntity> findByZdid(Integer zdid,String zxbz);

    List<DictionarylistEntity> findByCtdm(Integer zdid,Integer ctdm,String zxbz);

    List<DictionarylistEntity> findByCtz(Integer zdid,String ctmc,String zxbz);

    List<DictionarylistEntity> findByCtdmAndCtz(Integer zdid,Integer ctdm,String ctmc ,String zxbz);

    /*****************************************给其它模块提供的接口***********************************************/

    /**
     * 通过id查询单个词条
     * @param id
     * @return
     */
     DictionarylistEntity findById(Integer id);

    /*
     * 根据字典名称和词条代码查询词条名称
     */
    //   String  querDictListByZdzwmAndKey(String zdmc, String ctdm);
    /*
     * 根据字典代码和词条代码查询词条名称
     */
    //    String querDictListByZdywmAndKey(String zddm, String ctdm);

    /* 根据字典名称和词条代码查询词条名称*/
    //   String  querDictListByZdywmAndVule(String zddm, String ctmc);

     /*根据字典代码和词条代码查询词条名称*/
    //   String  querDictListByZdzwmAndVule(String zdmc, String ctmc);
}
