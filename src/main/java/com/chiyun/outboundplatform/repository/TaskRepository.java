package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TaskRepository extends CrudRepository<TaskEntity, Long> {
    /**
     *  通过id查询
     */
    TaskEntity findById(Integer id);


    /**
     *  查询所有
     */
    List<TaskEntity> findAll();

    /**
     *  保存
     */
    TaskEntity save(TaskEntity entity);

    /**
     *  通过id删除
     */
    @Query(value = "delete from task where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    /**
     * 通过任务状态分页查询
     */
    Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable);

}
