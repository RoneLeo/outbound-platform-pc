package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.OutboundmessageEntity;
import com.chiyun.outboundplatform.entity.UsermessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserMessageRepository extends CrudRepository<UsermessageEntity, Long> {
    /**
     *  通过id查询
     */
    UsermessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<UsermessageEntity> findAll();

    /**
     *  保存
     */
    UsermessageEntity save(UsermessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from usermessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
