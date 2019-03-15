package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Linqi on 2019-03-14.
 */
@Service
public class DictionaryListServiceImpl implements IdictionaryListService {

    @Resource
    private DictionaryListRepository dictionaryListRepository;

    /*****************************************查询操作*********************************************************/
    @Override
    public DictionaryListEntity findById(Integer id) {
        return dictionaryListRepository.findById(id);
    }

    @Override
    public List<DictionaryListEntity> findAll() {
        return dictionaryListRepository.findAll();
    }


    /**
     * 通过 字典英文名和字典项代号 查询字典项的值
     *
     * @param zdywm
     * @param key
     * @return
     */
    @Override
    public String querDictListByZdywmAndKey(String zdywm, String key) {
        return dictionaryListRepository.querDictListByZdywmAndKey(zdywm, key);
    }

    /**
     * 通过 字典中文名和字典项代号 查询字典项的值
     *
     * @param zdzwm
     * @param key
     * @return
     */
    @Override
    public String querDictListByZdzwmAndKey(String zdzwm, String key) {
        return dictionaryListRepository.querDictListByZdzwmAndKey(zdzwm, key);
    }

    /*****************************************新增操作*********************************************************/

    @Override
    public DictionaryListEntity save(DictionaryListEntity entity) {
        return dictionaryListRepository.save(entity);
    }


    /*****************************************删除操作*********************************************************/

    @Override
    public int deleteById(Integer id) {
        return dictionaryListRepository.deleteById(id);
    }

    @Override
    public int deleteByDid(Integer did) {
        return dictionaryListRepository.deleteByDid(did);
    }

    /*****************************************更新操作*********************************************************/

    @Override
    public int update(DictionaryListEntity entity) {
        return dictionaryListRepository.update(entity);
    }

    @Override
    public int zhuXiaoByDid(Integer did) {
        return dictionaryListRepository.deleteByDid(did);
    }


}
