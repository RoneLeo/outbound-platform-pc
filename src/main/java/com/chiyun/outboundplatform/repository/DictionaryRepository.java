package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long> {


     public DictionaryEntity findById(Integer id);

    /**
     *   通过字典中文名来查询字典
     * @param name
     * @return
     */
    @Query(value = "select * from dictionary where name like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryEntity>  findByName(String name);

    /**
     *   通过字典英文名来查询字典
     * @param eng_name
     * @return
     */
    @Query(value = "select * from dictionary where eng_name like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryEntity>  findByEng_Name(String eng_name);


    public List<DictionaryEntity> findAll();


    public  DictionaryEntity save(DictionaryEntity entity);


    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionary where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    public int deleteById(Integer id);

    /**
     *  更新字典
     * @param entity
     * @return
     */
    @Query(value = "update dictionary  dic set dic.name=#{#entity.name},dic.eng_name=#{# entity.eng_name},dic.type=#{#entity.type} where dic.id=#{#entity.id} "
            , nativeQuery = true)
    @Modifying
    @Transactional
     public int updateOne(DictionaryEntity entity);

    /**
     *  注销字典
     * @param id
     * @return
     */
    @Query(value = "update dictionary  dic set dic.zxbz='1' where dic.id=?1 and dic.zxbz='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     public int   zhuXiaoOne(Integer id);

}
