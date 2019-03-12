package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.DelaylvEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DelayLvRepository extends CrudRepository<DelaylvEntity, Long> {
    /**
     *  通过id查询
     */
    DelaylvEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<DelaylvEntity> findAll();

    /**
     *  保存
     */
    DelaylvEntity save(DelaylvEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from delaylv where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
