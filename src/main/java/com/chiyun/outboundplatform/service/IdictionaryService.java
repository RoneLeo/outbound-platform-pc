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
     *  通过 字典中文名查询
     * @param name
     * @return
     */
     List<DictionaryEntity>  findByName(String name);

    /**
     *  通过 字典英文名查询
     * @param eng_name
     * @return
     */
    List<DictionaryEntity>  findByEng_Name(String eng_name);
    /**
     * 查询所有
     * @return
     */
     List<DictionaryEntity> findAll();



    /**
     *  新增
     * @param entity
     * @return
     */
      DictionaryEntity save(DictionaryEntity entity);



    /**
     *  通过id删除
     */
     int deleteById(Integer id);

    /**
     *  更新字典
     * @param entity
     * @return
     */
    public int updateOne(DictionaryEntity entity);

    /**
     * 注销字典
     * @param id
     * @return
     */
    public int   zhuXiaoOne(Integer id);
}
