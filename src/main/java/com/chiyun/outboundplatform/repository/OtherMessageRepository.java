package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.LoantypeEntity;
import com.chiyun.outboundplatform.entity.OthermessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OtherMessageRepository extends CrudRepository<OthermessageEntity, Long> {
    /**
     *  通过id查询
     */
    OthermessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<OthermessageEntity> findAll();

    /**
     *  保存
     */
    OthermessageEntity save(OthermessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from othermessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
