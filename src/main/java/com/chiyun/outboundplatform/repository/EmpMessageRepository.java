package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.EmpmessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpMessageRepository extends CrudRepository<EmpmessageEntity, Long> {
    /**
     *  通过id查询
     */
    EmpmessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<EmpmessageEntity> findAll();

    /**
     *  保存
     */
    EmpmessageEntity save(EmpmessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from empmessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
