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


    List<DictionarylistEntity> findBydid(Integer zdid,String zxbz);

    List<DictionarylistEntity> findByCtdm(Integer zdid,String ctdm,String zxbz);

    List<DictionarylistEntity> findByCtz(Integer zdid,String ctmc,String zxbz);

    List<DictionarylistEntity> findByCtdmAndCtz(Integer zdid,String ctdm,String ctmc ,String zxbz);

    /*****************************************给其它模块提供的接口***********************************************/

    /**
     * 通过id查询单个
     * @param id
     * @return
     */
     DictionarylistEntity findById(Integer id);

    /*
     * 根据字典中文名和字典项代号查询字典项的值
     */
    //   String  querDictListByZdzwmAndKey(String zdzwm, String key);

    /*
     * 根据字典英文名和字典项代号查询字典项的值
     *
     */
    //    String querDictListByZdywmAndKey(String zdywm, String key);


}
