package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.StringUtil;
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
    public int updateOne(DictionaryListEntity entity) {
        return  dictionaryListRepository.updateOne(entity.getZdywm(),entity.getZdzwm(),entity.getC_id(),entity.getC_name(),entity.getDid(),entity.getId());

    }

    @Override
    public int zhuXiaoByDid(Integer did) {
        return dictionaryListRepository.zhuXiaoByDid(did);
    }

    @Override
    public int zhuXiaoOne(Integer id) {
        return dictionaryListRepository.zhuXiaoOne(id);
    }

    @Override
    public int updateZdywmAndZdzwnAndDid(String zdywm, String zdzwm, Integer Ndid, Integer Odid) {
        return dictionaryListRepository.updateZdywmAndZdzwnAndDid(zdywm,zdzwm,Ndid,Odid);
    }

    @Override
    public List<DictionaryListEntity> findBydid(Integer did) {
        return dictionaryListRepository.findBydid(did);
    }

    @Override
    public List<DictionaryListEntity> findByZdzwm(String zdzwm) {
        return dictionaryListRepository.findByZdzwm(zdzwm);
    }

    @Override
    public List<DictionaryListEntity> findByZdywm(String zdywm) {
        return dictionaryListRepository.findByZdywm(zdywm);
    }








}
