package com.chiyun.outboundplatform.service;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
   DictionaryEntity findById(Integer id);

    /**
     *  通过 字典中文名查询
     * @param name
     * @return
     */
     List<DictionaryEntity>  findDictByNameAndState(String name,String state);

    /**
     *  通过 字典英文名查询
     * @param eng_name
     * @return
     */
    List<DictionaryEntity>  findDictByEng_NameAndState(String eng_name ,String state);
    /**
     * 查询所有
     * @return
     */
     List<DictionaryEntity> findAll(String state);

    List<DictionaryEntity> findDictByNameAndEng_NameAndState (String name,String eng_name ,String state);

    /**
     *  新增//修改
     * @param entity
     * @return
     */
      Map<String,Object> save(DictionaryEntity entity);



    /**
     *  通过id删除
     */
     int deleteById(Integer id);

    /**
     *  更新字典
     * @param entity
     * @return
     */
   int update(DictionaryEntity entity);

    /**
     * 注销字典
     * @param id
     * @return
     */
   int   cancellationById(Integer id);

    /**
     * 激活字典
     * @param id
     * @return
     */
    int   unCancellationById(Integer id);

    /**
     * 通过id查询是否已存在
     * @return
     */
    Boolean existsById(Integer id);
}
