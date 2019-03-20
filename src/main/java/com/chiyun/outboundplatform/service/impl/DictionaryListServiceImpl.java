package com.chiyun.outboundplatform.service.impl;

import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import com.chiyun.outboundplatform.repository.DictionaryListRepository;
import com.chiyun.outboundplatform.service.IdictionaryListService;
import com.chiyun.outboundplatform.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
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


    @Override
    public DictionarylistEntity findById(Integer id) {
        return dictionaryListRepository.findById(id);
    }

    @Override
    public List<DictionarylistEntity> findBydid(Integer did,String zxbz) {
        if(StringUtil.isNull(zxbz)){
            return dictionaryListRepository.findBydid(did);
        }else{
            return dictionaryListRepository.findBydidAndZxbz(did, zxbz);
        }

    }

    @Override
    public List<DictionarylistEntity> findByCtdm(Integer did,Integer ctdm, String zxbz) {
        if(StringUtil.isNull(zxbz)){
            return  dictionaryListRepository.findByCtdm(did, ctdm);
        }else{
            return  dictionaryListRepository.findByCtdmAndZxbz(did, ctdm, zxbz);
        }
    }

    @Override
    public List<DictionarylistEntity> findByCtz(Integer did,String ctmc, String zxbz) {
        if(StringUtil.isNull(zxbz)){
         return  dictionaryListRepository.findByCtz(did, ctmc);
        }else{
        return  dictionaryListRepository.findByCtzAndZxbz(did, ctmc, zxbz);
        }
    }

    @Override
    public List<DictionarylistEntity> findByCtdmAndCtz(Integer did,Integer ctdm, String ctmc, String zxbz) {
        if (StringUtil.isNull(zxbz)) {
            return dictionaryListRepository.findByCtdmAndCtz(did, ctdm, ctmc);
        } else {
            return dictionaryListRepository.findByCtdmAndCtzAndZxbz(did, ctdm, ctmc, zxbz);
        }
    }






    /**
     *  新增字典项
     * @param entity
     * @return
     */
    @Override
    public Map<String,Object> save(DictionarylistEntity entity) {
        System.out.println(entity.toString());
        Map<String,Object> msg=new HashMap<String,Object>();
        //查询词条是否已存在
         int  cn=dictionaryListRepository.findByParms(entity.getCtdm(),entity.getCtmc(),entity.getZdid());
   if(cn==0){
       DictionarylistEntity entiy1= dictionaryListRepository.save(entity);
         if(entiy1==null){
             msg.put("fail","词条新增失败");
             return msg;
         }
            entiy1.setId(entiy1.getCtdm());//ID主键和词条代码保持一致，数据库主键是词条代码
            int con = updateOne(entiy1); //完善信息后在去存入
           msg.put("success",entiy1);
   }else{
       msg.put("fail","该词条信息已存在，请查看是否已被注销");
   }
         return msg;
    }


    @Override
    public int deleteById(Integer id) {
        return dictionaryListRepository.deleteById(id);
    }

    @Override
    public int deleteByDid(Integer did) {
        return dictionaryListRepository.deleteByDid(did);
    }


    @Override
    public int updateOne(DictionarylistEntity entity) {
        int cn=0;
        DictionarylistEntity  gxxx  =dictionaryListRepository.save(entity);
        if(gxxx==null){
            return  cn;
        }else{
            cn=1;
            return  cn;
        }


    }





    @Override
    public int cancellationDicListById(Integer id) {
        return dictionaryListRepository.cancellationDicListById(id);
    }

    @Override
    public int unCancellationDicListById(Integer id) {
        return dictionaryListRepository.unCancellationDicListById(id);
    }

/*****************************************给其它模块提供的接口词条信息查询***********************************************/




//    *
//     * 通过 字典英文名和字典项词条代码 查询字典项词条值
//     *
//     * @param zdywm
//     * @param ctdm
//     * @return
//
//   @Override
//    public String querDictListByZdywmAndKey(String zdywm, Integer ctdm) {
//        return dictionaryListRepository.querDictListByZdywmAndKey(zdywm, ctdm);
//    }
//
//     *
//     * 通过 字典名称和字典项词条代码 查询字典项词条值
//     *
//     * @param zdzwm
//     * @param ctdm
//     * @return
//
//    @Override
//    public String querDictListByZdzwmAndKey(String zdzwm, Integer ctdm) {
//        return dictionaryListRepository.querDictListByZdzwmAndKey(zdzwm, ctdm);
//    }




}
