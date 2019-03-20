package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.ColltypeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollTypeRepository extends CrudRepository<ColltypeEntity, Long> {
    /**
     *  通过id查询
     */
    ColltypeEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<ColltypeEntity> findAll();

    /**
     *  保存
     */
    ColltypeEntity save(ColltypeEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from colltype where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
