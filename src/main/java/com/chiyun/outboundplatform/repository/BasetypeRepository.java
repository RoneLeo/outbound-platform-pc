package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BasetypeRepository extends CrudRepository<BasetypeEntity, Long> {
    /**
     *  通过id查询
     */
    BasetypeEntity findById(Integer id);

    /**
     *  通过类型查询名称
     */
    @Query(value = "select name from basetype where type = ?1", nativeQuery = true)
    String findNameByType(Integer type);

    /**
     *  查询所有
     */
    List<BasetypeEntity> findAll();

    /**
     *  保存
     */
    BasetypeEntity save(BasetypeEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from basetype where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
