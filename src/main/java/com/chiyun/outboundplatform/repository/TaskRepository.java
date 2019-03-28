package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     *  查询所有
     */
    List<TaskEntity> findAll();

    /**
     *  保存
     */
    TaskEntity save(TaskEntity entity);

    /**
     * 通过任务状态分页查询
     */
    Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable);

    /**
     * 多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人、任务完成时间
     */
    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "if(?5 is not null, task_state = ?5, 1 = 1) and " +
            "if(?6 is not null, task_people = ?6, 1 = 1) and " +
            "complate_time between ?7 and ?8", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetweenAndRwwcsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                        Integer rwfs, Integer rwzt, Integer rwzxr,
                                        Date beginWcsj, Date endWcsj, Pageable pageable);

    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "if(?5 is not null, task_state = ?5, 1 = 1) and " +
            "if(?6 is not null, task_people = ?6, 1 = 1)", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                        Integer rwfs, Integer rwzt, Integer rwzxr, Pageable pageable);

    @Query(value = "select * from task where task_name like ?1 and " +
            "if(?2 is not null, task_way = ?2, 1 = 1) and " +
            "if(?3 is not null, task_state = ?3, 1 = 1) and " +
            "if(?4 is not null, task_people = ?4, 1 = 1) and " +
            "complate_time between ?5 and ?6", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwwcsjBetween(String rwmc, Integer rwfs, Integer rwzt, Integer rwzxr,
                                        Date beginWcsj, Date endWcsj, Pageable pageable);

    @Query(value = "select * from task where task_name like ?1 and " +
            "if(?2 is not null, task_way = ?2, 1 = 1) and " +
            "if(?3 is not null, task_state = ?3, 1 = 1) and " +
            "if(?4 is not null, task_people = ?4, 1 = 1)", nativeQuery = true)
    Page<TaskEntity> findAllByCondition(String rwmc, Integer rwfs, Integer rwzt, Integer rwzxr, Pageable pageable);
}
