package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BatchEntity;
import com.chiyun.outboundplatform.entity.CardmessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CardMessageRepository extends CrudRepository<CardmessageEntity, Long> {
    /**
     *  通过id查询
     */
    CardmessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<CardmessageEntity> findAll();

    /**
     *  保存
     */
    CardmessageEntity save(CardmessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from cardmessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
