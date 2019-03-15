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

    /**
     * 通过字典中文名来查询字典项
     * @param zdzwm
     * @return
     */
    @Query(value = "select * from dictionarylist where zdzwm like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryListEntity>  findByZdzwm(String zdzwm);

    /**
     *  通过字典英文名来查询字典项
     * @param zdywm
     * @return
     */
    @Query(value = "select * from dictionarylist where zdywm like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryListEntity>  findByZdywm(String zdywm);



      DictionaryListEntity save(DictionaryListEntity entity);


    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
     int deleteById(Integer id);


    /**
     * 通过did 外键 批量删除
     * @param did
     * @return
     */
    @Query(value = "delete from dictionarylist where did = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteByDid(Integer did);


    /**
     * 根据字典英文名和字典项代号查询字典项的值
     * @param zdywm
     * @param key
     * @return
     */
     @Query(value = "select c_name from dictionarylist where zdywm = ? and c_id= ? ", nativeQuery = true)
       String querDictListByZdywmAndKey(String zdywm, String key);

    /**
     *  根据字典中文名和字典项代号查询字典项的值
     * @param zdzwm
     * @param key
     * @return
     */
     @Query(value = "select c_name from dictionarylist where zdyzm = ? and c_id= ? ", nativeQuery = true)
       String  querDictListByZdzwmAndKey(String zdzwm, String key);


    /**
     * 更新 字典项信息
     * @param entity
     * @return
     */
      @Query(value = "update  dictionarylist dicList set  dicList.zdywm=#{# entity.zdywm},dicList.zdzwm=#{# entity.zdzwm},dicList.c_id=#{# entity.c_id} where dicList.id =#{# entity.id} ", nativeQuery = true)
      @Modifying
      @Transactional
      int update(DictionaryListEntity entity);

    /**
     * 通过外键批量注销字典项
     * @param did
     * @return
     */
      @Query(value = "update  dictionarylist  set zxbz='1'  where did =?1 ", nativeQuery = true)
      @Modifying
      @Transactional
      int zhuXiaoByDid(Integer did);

}
