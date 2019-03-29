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

    List<DictionarylistEntity> queryByEntity(DictionarylistEntity entity,boolean isSort);


    /*****************************************给其它模块提供的接口***********************************************/

    /**
     * 通过id查询单个词条，用于其他模块获取词条信息
     * @param id
     * @return
     */
     DictionarylistEntity findById(Integer id);


    /**
     * 根据字典名称和词条代码查询词条名称 --用于导出数据时把字典字段的代码转换成中文名
     */
      String  queryCtmcByZdmcAndCtdm(String zdmc, String ctdm);
    /**
     * 根据字典代码和词条代码查询词条名称 --用于导出数据时把字典字段的代码转换成中文名
     */
     String queryCtmcByZddmAndCtdm(String zddm, String ctdm);

    /**
    * 根据字典名称和词条名称查询词条代码 --用于导入数据时把字典字段的词条名称转换成对应的词条代码
    */
     String  queryCtdmByZddmAndCtmc(String zddm, String ctmc);

    /**
    * 根据字典名称和词条名称查询词条代码 --用于导入数据时把字典字段的词条名称转换成对应的词条代码
    */
     String  queryCtdmByZdmcAndCtmc(String zdmc, String ctmc);

    /*********************便于开发调试提供的方法-在后期上线时需要删除掉****************************************************/
    /*
     *  通过id删除
     */

    int deleteById(Integer id);

    /*
     * 通过did 外键 批量删除
     *
     */
    int deleteByDid(Integer did);

}
