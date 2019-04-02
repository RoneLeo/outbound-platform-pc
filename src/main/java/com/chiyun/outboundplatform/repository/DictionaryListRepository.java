package com.chiyun.outboundplatform.repository;


import com.chiyun.outboundplatform.entity.DictionarylistEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryListRepository extends CrudRepository<DictionarylistEntity , Long>,JpaSpecificationExecutor {




    @Query(value = "select * from dictionarylist  ORDER BY FIELD (state,'0','1')", nativeQuery = true)
     List<DictionarylistEntity> findAll();

    List<DictionarylistEntity> findAll(Specification querySpecifi, Sort sort);

    List<DictionarylistEntity> findAll(Specification querySpecifi);





 /**
  *   词条查询是否已存在
  * @param ctdm
  * @param ctmc
  * @param zdid
  * @return
  */
 @Query(value="select count(*) from dictionarylist where (entrycode =?1 or entryvalue=?2) and dictid=?3 ",nativeQuery = true)
    int findByCtdmAndCtmcAndZdid(Integer ctdm,String ctmc,Integer zdid);



    DictionarylistEntity save(DictionarylistEntity entity);




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


    @Query(value = "update dictionarylist   set dictname=?1, dicteng_name=?2 where dictid=?3" , nativeQuery = true)
    @Modifying
    @Transactional
    void  updateForDictnameAndDictengname (String dictName,String dictdm,int dictId);

    /**************************************给其它模块提供的功能方法********************************************************/
    @Query(value = "select * from dictionarylist where  id=?1",nativeQuery = true)
    DictionarylistEntity findById(Integer id);


    @Query(value = "select * from dictionarylist where  dictid=?1 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionarylistEntity> findByZdid(Integer zdid);

    @Query(value = "select * from dictionarylist where  dictname=?1 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionarylistEntity> queryCtxxByZdmc(String zdmc);


    @Query(value = "select * from dictionarylist where  dicteng_name=?1 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionarylistEntity> queryCtxxByZddm(String zddm);

    /**
     * 根据字典名称和词条代码查询词条名称 --用于导出数据时把字典字段的代码转换成中文名
     */
    @Query(value = "select entryvalue from dictionarylist where dictname=?1 and entrycode=?2",nativeQuery = true)
    String  queryCtmcByZdmcAndCtdm(String zdmc, String ctdm);
    /**
     * 根据字典代码和词条代码查询词条名称 --用于导出数据时把字典字段的代码转换成中文名
     */
    @Query(value = "select entryvalue from dictionarylist where dicteng_name=?1 and entrycode=?2",nativeQuery = true)
    String queryCtmcByZddmAndCtdm(String zddm, String ctdm);

    /**
     * 根据字典名称和词条名称查询词条代码 --用于导入数据时把字典字段的词条名称转换成对应的词条代码
     */
    @Query(value = "select entrycode from dictionarylist where dicteng_name=?1 and entryvalue=?2",nativeQuery = true)
    String  queryCtdmByZddmAndCtmc(String zddm, String ctmc);

    /**
     * 根据字典名称和词条名称查询词条代码 --用于导入数据时把字典字段的词条名称转换成对应的词条代码
     */
    @Query(value = "select entrycode from dictionarylist where dictname=?1 and entryvalue=?2",nativeQuery = true)
    String  queryCtdmByZdmcAndCtmc(String zdmc, String ctmc);


    /*********************便于开发调试提供的方法-在后期上线时需要删除掉****************************************************/

    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);


    @Query(value = "delete from dictionarylist where dictid = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByDid(Integer zdid);

}
