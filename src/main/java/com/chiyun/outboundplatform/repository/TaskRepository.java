package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     *  保存
     */
    TaskEntity save(TaskEntity entity);

    /**
     * 通过任务状态分页查询
     */
    Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable);

    List<TaskEntity> findAllByAjid(Integer ajid);

    /**
     *  根据案件id修改任务状态：注销
     */
    @Query(value = "update task set task_state = '8' where case_id = ?1", nativeQuery = true)
    void resetByAjid(Integer ajid);

    /**
     * 通过案件id查询任务
     */
    Page<TaskEntity> findAllByRwztInAndAjidInOrderByRwztDesc(List<Integer> rwzts, List<Integer> ajids, Pageable pageable);


    /**
     *  业务员查询所有
     */
    Page<TaskEntity> findAllByRwzxrOrderByIdDesc(Integer rwzxr, Pageable pageable);

    /**
     *  获取最早的任务截止时间和任务完成时间
     */
    @Query(value = "select task_time from task order by task_time asc limit 1", nativeQuery = true)
    Date getEarliestRwjzsj();

    @Query(value = "select task_time from task order by task_time desc limit 1", nativeQuery = true)
    Date getLatestRwjzsj();

    @Query(value = "select complate_time from task order by complate_time asc limit 1", nativeQuery = true)
    Date getEarliestRwwcsj();

    @Query(value = "select complate_time from task order by complate_time desc limit 1", nativeQuery = true)
    Date getLatestRwwcsj();

    @Query(value = "select create_time from task order by create_time desc limit 1", nativeQuery = true)
    Date getLatestRwcjsj();

    @Query(value = "select create_time from task order by create_time desc limit 1", nativeQuery = true)
    Date getEarliestRwcjsj();


    /**
     *  统计业务员 已接收、已处理案件数、应得佣金及实际佣金
     */
    @Query(value = "select count(id) from task where task_people = ?1 and task_state = ?2", nativeQuery = true)
    int countAllByRwzxrAndRwzt(Integer rwzxr, Integer rwzt);

    @Query(value = "select sum(task_money) from task where task_people = ?1", nativeQuery = true)
    Double sumAllRwyjByRwzxr(Integer rwzxr);

    @Query(value = "select sum(actual_money) from task where task_people = ?1", nativeQuery = true)
    Double sumAllSjyjByRwzxr(Integer rwzxr);

    /**
     *  通过案件id统计任务佣金
     */
    @Query(value = "select sum(task_money) from task where case_id = ?1", nativeQuery = true)
    Double sumAllRwyjByAjid(Integer ajid);

    /**
     *  通过任务执行人、任务状态查询
     */
    Page<TaskEntity> findAllByRwzxrAndRwzt(Integer ywyid, Integer rwzt, Pageable pageable);

    /**
     * 业务员单框查询
     */
    @Query(value = "SELECT * FROM task WHERE CONCAT(task_name, task_time, task_money, task_description, task_way, task_state, check_remark, actual_money, task_people, complate_time, create_time, update_time) LIKE ?1 or CONCAT(task_name, task_time, task_money, task_description, task_way, task_state, check_remark, actual_money, task_people, complate_time, create_time, update_time) is null and case_id in (SELECT id from casebasemessage where area_id in (SELECT area_code from user where id = ?2)) ", nativeQuery = true)
    Page<TaskEntity> findAllByCondition(String param, Integer ywyid, Pageable pageable);

    /**
     * 多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人、任务完成时间
     */
    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "if(?5 is not null, task_state = ?5, 1 = 1) and " +
            "if(?6 is not null, task_people = ?6, 1 = 1) and " +
            "create_time between ?7 and ?8", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetweenAndRwcjsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                        Integer rwfs, Integer rwzt, Integer rwzxr,
                                        Date beginCjsj, Date endCjsj, Pageable pageable);



    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "if(?5 is not null, task_people = ?5, 1 = 1) and " +
            "complate_time between ?6 and ?7 and " +
            "create_time between ?8 and ?9 and task_state = '4'", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetweenAAndRwcjsjBetweenAndRwcjsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                        Integer rwfs, Integer rwzxr, Date beginWcsj, Date endWcsj,
                                                        Date beginCjsj, Date endCjsj, Pageable pageable);

}
