package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FieldcasebaseEntity;
import com.chiyun.outboundplatform.entity.LinkmanmessageEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LinkmanMessageRepository extends CrudRepository<LinkmanmessageEntity, Long> {
    /**
     *  通过id查询
     */
    LinkmanmessageEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<LinkmanmessageEntity> findAll();

    /**
     *  保存
     */
    LinkmanmessageEntity save(LinkmanmessageEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from linkmanmessage where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
