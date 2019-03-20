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
    List<DictionarylistEntity>  findByCtdm(Integer did,Integer ctdm);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and state=?3", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndZxbz(Integer did,Integer ctdm,String zxbz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entryvalue like concat('%',?2,'%')", nativeQuery = true)
    List<DictionarylistEntity>  findByCtz(Integer did,String ctz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entryvalue like concat('%',?2,'%') and state=?3", nativeQuery = true)
    List<DictionarylistEntity>  findByCtzAndZxbz(Integer did,String ctz,String zxbz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and entryvalue like concat('%',?3,'%')", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndCtz(Integer did,Integer ctdm,String ctz);

    @Query(value = "select * from dictionarylist where  dictid=?1 and entrycode like concat('%',?2,'%') and entryvalue like concat('%',?3,'%') and state=?4", nativeQuery = true)
    List<DictionarylistEntity>  findByCtdmAndCtzAndZxbz(Integer did,Integer ctdm,String ctz ,String zxbz);

 /**
  *   词条查询是否已存在
  * @param ctdm
  * @param ctz
  * @param zdid
  * @return
  */
 @Query(value="select count(*) from dictionarylist where (entrycode =?1 or entryvalue=?2) and dictid=?3 ",nativeQuery = true)
    int findByParms(Integer ctdm,String ctz,Integer zdid);



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
     * 注销单个
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set state='1' where id=?1 and state='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int    cancellationDicListById(Integer id);

    /**
     * 激活 单个
     * @param id
     * @return
     */
    @Query(value = "update dictionarylist   set state='0' where id=?1 and state='1'" , nativeQuery = true)
    @Modifying
    @Transactional
    int   unCancellationDicListById(Integer id);

    /**************************************给其它模块提供的功能方法********************************************************/

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
     * 通过外键批量注销字典项【词条】
     * @param zdid
     * @return
     */
//      @Query(value = "update  dictionarylist  set state='1'  where dictid =?1 and state='0'", nativeQuery = true)
//      @Modifying
//      @Transactional
//      int  cancellationDicListByZdid(Integer zdid);
//
    /*
     * 通过外键批量激活字典项【词条】
     * @param zdid
     * @return
     */
//    @Query(value = "update  dictionarylist  set state='0'  where dictid =?1 and state='1'", nativeQuery = true)
//    @Modifying
//    @Transactional
//    int unCancellationDicListByZdid(Integer did);

      //通过字典代码和词条代码 查询词条名称
//    @Query(value = "select c_name from dictionarylist where dicteng_name = ?1 and entrycode= ?2  and state='0'", nativeQuery = true)
//    String querDictListByZdywmAndKey(String zdywm, Integer key);

      // 通过字典名称和词条代码 查询词条名称
//    @Query(value = "select c_name from dictionarylist where dictname = ?1 and entrycode= ?2 and state='0'", nativeQuery = true)
//    String  querDictListByZdzwmAndKey(String zdzwm, Integer key);


}
