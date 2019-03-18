package com.chiyun.outboundplatform.repository;


import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryListRepository extends CrudRepository<DictionaryListEntity , Long> {


     DictionaryListEntity findById(Integer id);


     List<DictionaryListEntity> findAll();


    @Query(value = "select * from dictionarylist where zdzwm like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryListEntity>  findByZdzwm(String zdzwm);


    @Query(value = "select * from dictionarylist where zdywm like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryListEntity>  findByZdywm(String zdywm);

    @Query(value = "select * from dictionarylist where  did=?1", nativeQuery = true)
    List<DictionaryListEntity> findBydid(Integer did);

    @Query(value = "select c_name from dictionarylist where zdywm = ?1 and c_id= ?2 ", nativeQuery = true)
    String querDictListByZdywmAndKey(String zdywm, String key);


    @Query(value = "select c_name from dictionarylist where zdzwm = ?1 and c_id= ?2 ", nativeQuery = true)
    String  querDictListByZdzwmAndKey(String zdzwm, String key);



    DictionaryListEntity save(DictionaryListEntity entity);



    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
     int deleteById(Integer id);


    @Query(value = "delete from dictionarylist where did = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByDid(Integer did);


    /**
     * 更新  单个字典项信息
     * @param zdywm
     * @param zdzwm
     * @param c_id
     * @param c_name
     * @param did
     * @param id
     * @return
     */
      @Query(value = "update  dictionarylist dicList set  dicList.zdywm=?1," +
              "dicList.zdzwm=?2 ,dicList.c_id=?3 ," +
              "dicList.c_name=?4 ,dicList.did=?5 "+
              "where dicList.id =?6", nativeQuery = true)
      @Modifying
      @Transactional
      int updateOne(String zdywm,String zdzwm,String c_id,String c_name,Integer did, Integer id);

    /**
     *  批量更新 字典项关于字典的信息
     * @param zdywm
     * @param zdzwm
     * @param Ndid
     * @param Odid
     * @return
     */
    @Query(value = "update  dictionarylist dicList set  dicList.zdywm=?1," +
                   "dicList.zdzwm=?2,dicList.c_id=?3  " +
                   "where dicList.did =?4 ", nativeQuery = true)
    @Modifying
    @Transactional
      int updateZdywmAndZdzwnAndDid(String zdywm,String zdzwm ,Integer Ndid,Integer Odid);

    /**
     * 通过外键批量注销字典项
     * @param did
     * @return
     */
      @Query(value = "update  dictionarylist  set zxbz='1'  where did =?1 and zxbz='0'", nativeQuery = true)
      @Modifying
      @Transactional
      int zhuXiaoByDid(Integer did);

    /**
     * 注销单个
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set zxbz='1' where id=?1 and zxbz='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int   zhuXiaoOne(Integer id);

}
