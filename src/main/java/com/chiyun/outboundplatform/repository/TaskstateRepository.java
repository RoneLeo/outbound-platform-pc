package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.BasetypeEntity;
import com.chiyun.outboundplatform.entity.TaskstateEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskstateRepository extends CrudRepository<TaskstateEntity, Long> {
    /**
     *  通过id查询
     */
    TaskstateEntity findById(Integer id);

    /**
     *  查询所有
     */
    List<TaskstateEntity> findAll();

    /**
     *  保存
     */
    TaskstateEntity save(TaskstateEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from taskstate where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

}
