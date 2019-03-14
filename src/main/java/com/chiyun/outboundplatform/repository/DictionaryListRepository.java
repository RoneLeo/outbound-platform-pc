package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.DictionaryListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryListRepository extends CrudRepository<DictionaryListEntity , Long> {

    /**
     * 通过id查询单个
     * @param id
     * @return
     */
    public DictionaryListEntity findById(Integer id);

    /**
     * 查询所有
     * @return
     */
    public List<DictionaryListEntity> findAll();

    /**
     * 通过对象信息查询 自定义类需要自己写实现SQL
     * @param entity
     * @return
     */
   // public List<DictionaryListEntity> queryByEntity(DictionaryListEntity entity);

    /**
     * 新增
     * @param entity
     * @return
     */
    public  DictionaryListEntity save(DictionaryListEntity entity);
    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionarylist where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    public int deleteById(Integer id);

    /**
     *  单个对象信息修改
     * @param entity
     * @return
     */
   // @Transactional
  //  public int update(DictionaryListEntity entity);
}
