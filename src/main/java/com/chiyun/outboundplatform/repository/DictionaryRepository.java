package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long> {

    /**
     *   通过id查询
     * @param id
     * @return
     */
     public DictionaryEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<DictionaryEntity> findAll();

    /**
     *  新增
     * @param entity
     * @return
     */
    public  DictionaryEntity save(DictionaryEntity entity);

    /**
     * 通过对象信息查询 自定义类需要自己写实现SQL
     * @param entity
     * @return
     */
   // public  List<DictionaryEntity> queryByEntity(DictionaryEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionary where id = ?", nativeQuery = true)
    @Modifying
    @Transactional
    public int deleteById(Integer id);
}
