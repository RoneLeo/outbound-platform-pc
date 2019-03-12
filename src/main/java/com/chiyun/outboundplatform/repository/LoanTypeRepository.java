package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.LoanmessageEntity;
import com.chiyun.outboundplatform.entity.LoantypeEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LoanTypeRepository extends CrudRepository<LoantypeEntity, Long> {
    /**
     *  通过id查询
     */
    LoantypeEntity findById(Integer id);

    /**
     *  查询所以
     */
    List<LoantypeEntity> findAll();

    /**
     *  保存
     */
    LoantypeEntity save(LoantypeEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from loantype where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
