package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.CasemanagementEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CaseManagementRepository extends CrudRepository<CasemanagementEntity, Long> {
    /**
     *  通过id查询
     */
    CasemanagementEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<CasemanagementEntity> findAll();

    /**
     *  保存
     */
    CasemanagementEntity save(CasemanagementEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from casemanagement where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
