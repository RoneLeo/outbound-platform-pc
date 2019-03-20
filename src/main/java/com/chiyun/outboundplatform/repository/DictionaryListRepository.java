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



    @Query(value = "select * from dictionarylist where  dictid=?1", nativeQuery = true)
    List<DictionarylistEntity> findBydid(Integer did);


    @Query(value = "select * from dictionarylist where  dictid=?1 and state=?2", nativeQuery = true)
    List<DictionarylistEntity> findBydidAndZxbz(Integer did,String zxbz);


    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') ", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdm(Integer did,String ctdm);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and state=?3", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndZxbz(Integer did,String ctdm,String zxbz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entryvalue like concat('%',?2,'%')", nativeQuery = true)
    List<DictionarylistEntity>  findByCtz(Integer did,String ctz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entryvalue like concat('%',?2,'%') and state=?3", nativeQuery = true)
    List<DictionarylistEntity>  findByCtzAndZxbz(Integer did,String ctz,String zxbz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and entryvalue like concat('%',?3,'%')", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndCtz(Integer did,String ctdm,String ctz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and entryvalue like concat('%',?3,'%') and state=?4", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndCtzAndZxbz(Integer did,String ctdm,String ctz ,String zxbz);

 /**
  *   词条查询是否已存在
  * @param ctdm
  * @param ctz
  * @param zdid
  * @return
  */
 @Query(value="select count(*) from dictionarylist where (entrycode =?1 or entryvalue=?2) and dictid=?3 ",nativeQuery = true)
    int findByParms(String ctdm,String ctz,Integer zdid);



//    @Query(value = "select c_name from dictionarylist where dicteng_name = ?1 and entrycode= ?2 ", nativeQuery = true)
//    String querDictListByZdywmAndKey(String zdywm, String key);

//    @Query(value = "select c_name from dictionarylist where dictname = ?1 and entrycode= ?2 ", nativeQuery = true)
//    String  querDictListByZdzwmAndKey(String zdzwm, String key);



    DictionarylistEntity save(DictionarylistEntity entity);



    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
     int deleteById(Integer id);


    @Query(value = "delete from dictionarylist where dictid = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByDid(Integer did);




//    /**  预留方法
//     *  批量更新 字典项关于字典的信息
//     * @param zdywm
//     * @param zdzwm
//     * @param Ndid
//     * @param Odid
//     * @return
//     */
//    @Query(value = "update  dictionarylist dicList set  dicList.dicteng_name=?1," +
//                   "dicList.dictname=?2,dicList.dictid=?3  " +
//                   "where dicList.dictid =?4 ", nativeQuery = true)
//    @Modifying
//    @Transactional
//      int updateZdywmAndZdzwnAndDid(String zdywm,String zdzwm ,Integer Ndid,Integer Odid);

    /*
     * 通过外键批量注销字典项
     * @param zdid
     * @return
     */
//      @Query(value = "update  dictionarylist  set state='1'  where dictid =?1 and state='0'", nativeQuery = true)
//      @Modifying
//      @Transactional
//      int  cancellationDicListByZdid(Integer zdid);
//

//    @Query(value = "update  dictionarylist  set state='0'  where dictid =?1 and state='1'", nativeQuery = true)
//    @Modifying
//    @Transactional
//    int unCancellationDicListByZdid(Integer did);

    /**
     * 注销单个
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set state='1' where id=?1 and state='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int    cancellationDicListById(Integer id);

    /**
     * 激活
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set state='0' where id=?1 and state='1'" , nativeQuery = true)
    @Modifying
    @Transactional
    int   unCancellationDicListById(Integer id);

}
