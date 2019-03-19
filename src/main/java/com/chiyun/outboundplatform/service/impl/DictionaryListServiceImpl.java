package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Linqi on 2019-03-14.
 */
@Service
public class DictionaryListServiceImpl implements IdictionaryListService {

    @Resource
    private DictionaryListRepository dictionaryListRepository;

    /*****************************************查询操作*********************************************************/
    @Override
    public DictionarylistEntity findById(Integer id) {
        return dictionaryListRepository.findById(id);
    }

    @Override
    public List<DictionarylistEntity> findAll(String state) {
         if(StringUtil.isNull(state)){
             return dictionaryListRepository.findAll();
         }else{
             return  dictionaryListRepository.findAllByState(state);
         }

    }


    /**
     * 通过 字典英文名和字典项词条代码 查询字典项词条值
     *
     * @param zdywm
     * @param ctdm
     * @return
     */
    @Override
    public String querDictListByZdywmAndKey(String zdywm, String ctdm) {
        return dictionaryListRepository.querDictListByZdywmAndKey(zdywm, ctdm);
    }

    /**
     * 通过 字典中文名和字典项词条代码 查询字典项词条值
     *
     * @param zdzwm
     * @param ctdm
     * @return
     */
    @Override
    public String querDictListByZdzwmAndKey(String zdzwm, String ctdm) {
        return dictionaryListRepository.querDictListByZdzwmAndKey(zdzwm, ctdm);
    }

    /*****************************************新增操作*********************************************************/

    @Override
    public Map<String,Object> save(DictionarylistEntity entity) {
        Map<String,Object> msg=new HashMap<String,Object>();
        //查询是否已存在
            dictionaryListRepository.findByParms(entity.getCtdm(),entity.getCtz(),entity.getZdid());

         dictionaryListRepository.save(entity);

         return msg;
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
    public int updateOne(DictionarylistEntity entity) {
        int cn=0;
        return  cn;
    }

    @Override
    public int zhuXiaoByDid(Integer did) {
        return dictionaryListRepository.cancellationByDid(did);
    }

    @Override
    public int zhuXiaoOne(Integer id) {
        return dictionaryListRepository.cancellationById(id);
    }

    @Override
    public int updateZdywmAndZdzwnAndDid(String zdywm, String zdzwm, Integer Ndid, Integer Odid) {
        return dictionaryListRepository.updateZdywmAndZdzwnAndDid(zdywm,zdzwm,Ndid,Odid);
    }

    @Override
    public List<DictionarylistEntity> findBydid(Integer did) {
        return dictionaryListRepository.findBydid(did);
    }

    @Override
    public List<DictionarylistEntity> findByZdzwm(String zdzwm) {
        return dictionaryListRepository.findByZdzwm(zdzwm);
    }

    @Override
    public List<DictionarylistEntity> findByZdywm(String zdywm) {
        return dictionaryListRepository.findByZdywm(zdywm);
    }








}
