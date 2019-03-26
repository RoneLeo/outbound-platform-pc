package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.CasebasemessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wazto on 2019/3/21.
 */
public interface CasebasemessageRepository extends JpaRepository<CasebasemessageEntity, Integer> {

    Page<CasebasemessageEntity> findAllByPch(String pch, Pageable pageable);

    /**
     *  通过id查询
     */
//    CasebasemessageEntity findById(Integer id);

    /**
     *  查询所有
     */
    List<CasebasemessageEntity> findAll();

    /**
     *  保存
     */
    CasebasemessageEntity save(CasebasemessageEntity entity);

    /**
     *  通过id删除
     */
//    @Query(value = "delete from casebasemessage where id = ?1", nativeQuery = true)
//    @Modifying
//    @Transactional
//    int deleteById(Integer id);

    Page<CasebasemessageEntity> findAll(Pageable pageable);

    /**
     *  通过区域查询
     */
    Page<CasebasemessageEntity> findAllByAjqy(Integer ajqy, Pageable pageable);

}


