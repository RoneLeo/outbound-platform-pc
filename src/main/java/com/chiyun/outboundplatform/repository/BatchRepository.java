package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.BatchEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BatchRepository extends CrudRepository<BatchEntity, Long> {
    /**
     *  通过id查询
     */
    BatchEntity findById(Integer id);

    /**
     *  查询所有
     */
    List<BatchEntity> findAll();

    /**
     *  通过批次id查询
     */
    List<BatchEntity> findAllByPcidOrderBySort(String pcid);

    /**
     *  查询所有的类型
     */
    @Query(value = "select fieldcasebase_id from batch where batch_id = ?1", nativeQuery = true)
    List<Integer> findAllJczdidsByPcid(String pcid);
    /**
     *  保存
     */
    BatchEntity save(BatchEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from batch where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
