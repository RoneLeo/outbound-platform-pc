package com.chiyun.outboundplatform.repository;


import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryListRepository extends CrudRepository<DictionarylistEntity , Long> {


     DictionarylistEntity findById(Integer id);


     List<DictionarylistEntity> findAll();

    List<DictionarylistEntity> findAllByState(String state);


    @Query(value = "select * from dictionarylist where dictname like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionarylistEntity>  findByZdzwm(String zdzwm);


    @Query(value = "select * from dictionarylist where zdywm like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionarylistEntity>  findByZdywm(String zdywm);

    @Query(value = "select * from dictionarylist where  did=?1", nativeQuery = true)
    List<DictionarylistEntity> findBydid(Integer did);
    @Query(value="select count(*) from dictionarylist where =?1 and ",nativeQuery = true)
    List<DictionarylistEntity>findByParms(String ctdm,String ctz,Integer zdid);

    @Query(value = "select c_name from dictionarylist where zdywm = ?1 and c_id= ?2 ", nativeQuery = true)
    String querDictListByZdywmAndKey(String zdywm, String key);

    @Query(value = "select c_name from dictionarylist where dictname = ?1 and c_id= ?2 ", nativeQuery = true)
    String  querDictListByZdzwmAndKey(String zdzwm, String key);



    DictionarylistEntity save(DictionarylistEntity entity);



    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
     int deleteById(Integer id);


    @Query(value = "delete from dictionarylist where dictid = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByDid(Integer did);




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
      @Query(value = "update  dictionarylist  set state='1'  where did =?1 and state='0'", nativeQuery = true)
      @Modifying
      @Transactional
      int  cancellationByDid(Integer did);

    /**
     * 注销单个
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set zxbz='1' where id=?1 and zxbz='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int    cancellationById(Integer id);

}
