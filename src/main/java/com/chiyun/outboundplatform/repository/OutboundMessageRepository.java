package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.OthermessageEntity;
import com.chiyun.outboundplatform.entity.OutboundmessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OutboundMessageRepository extends CrudRepository<OutboundmessageEntity, Long> {
    /**
     *  通过id查询
     */
    OutboundmessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<OutboundmessageEntity> findAll();

    /**
     *  保存
     */
    OutboundmessageEntity save(OutboundmessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from outboundmessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
