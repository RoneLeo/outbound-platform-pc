package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long>,JpaSpecificationExecutor {


    DictionaryEntity save(DictionaryEntity entity);


      Boolean existsById(Integer id);

     DictionaryEntity findById(Integer id);

    /**
     *   通过字典中文名来查询字典
     * @param
     * @return
     */
   @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionaryEntity>  findByZdmc(String zdmc);

    /**
     *   通过字典中文名和字典状态赛选来查询字典
     * @param
     * @return
     */
   @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and state=?2 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionaryEntity>  findByZdmcAndZxbz(String zdmc,String zxbz);

    /**
     *   通过字典英文名查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where eng_name like  concat('%',?1,'%') ORDER BY FIELD (state,'0','1') ", nativeQuery = true)
    List<DictionaryEntity>  findByZddm(String zddm);

    /**
     *   通过字典代码和注销标志赛选查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where eng_name like  concat('%',?1,'%')  and state=?2 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionaryEntity>  findByZddmAndZxbz(String zddm,String zxbz);

    @Query(value = "select * from dictionary where name =?1  or eng_name=?2 ORDER BY FIELD (state,'0','1') ", nativeQuery = true)
    List<DictionaryEntity>  findDictByZdmcAndZddm(String zdmc,String zddm);


    @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and eng_name  like  concat('%',?2,'%') ORDER BY FIELD (state,'0','1') ", nativeQuery = true)
    List<DictionaryEntity>   findByZdmcAndZddm(String zdmc,String zddm);


    @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and eng_name like  concat('%',?2,'%')  and state=?3 ORDER BY FIELD (state,'0','1')", nativeQuery = true)
    List<DictionaryEntity>  findByZdmcAndZddmAndZxbz (String zdmc,String zddm ,String zxbz);

    /**
     * 查询所有的数据
     * @return
     */
    @Query(value = "select * from dictionary  ORDER BY FIELD (state,'0','1')",nativeQuery = true)
    List<DictionaryEntity> findAll();
    /**
     * 查询所有的数据
     * @return
     */
    @Query(value = "select * from dictionary where state=?1 ORDER BY FIELD (state,'0','1')",nativeQuery = true)
    List<DictionaryEntity> findAllByZxbz(String zxbz);

    List<DictionaryEntity> findAll(Specification querySpecifi, Sort sort);

    /**
     *  注销字典
     * @param id
     * @return
     */
    @Query(value = "update dictionary  dic set dic.state='1' where dic.id=?1 and dic.state='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int   cancellationById(Integer id);

    @Query(value = "update dictionary  dic set dic.state='0' where dic.id=?1 and dic.state='1'" , nativeQuery = true)
    @Modifying
    @Transactional
     int   unCancellationById(Integer id);


    /*********************便于开发调试提供的方法-在后期上线时需要删除掉****************************************************/

    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionary where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);


}
