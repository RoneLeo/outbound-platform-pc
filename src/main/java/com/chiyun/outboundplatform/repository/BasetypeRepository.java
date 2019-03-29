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
     *  查询所有
     */
    List<BasetypeEntity> findAll();


}
