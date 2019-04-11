package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    /**
     * 保存
     */
    TaskEntity save(TaskEntity entity);

    /**
     * 通过案件id和任务状态查询
     */
    Page<TaskEntity> findAllByAjidAndRwztOrderByRwcjsj(Integer ajid, Integer rwzt, Pageable pageable);

    /**
     * 通过任务状态分页查询
     */
    @Query(value = "select * from task where task_state = ?1 and case_id in (select distinct id from casebasemessage where show_state = '1')", nativeQuery = true)
    Page<TaskEntity> findAllByRwzt(Integer rwzt, Pageable pageable);

    List<TaskEntity> findAllByAjid(Integer ajid);

    Page<TaskEntity> findAllByAjidOrderByRwcjsj(Integer ajid, Pageable pageable);

    /**
     * 根据id修改任务状态
     */
    @Query(value = "update task set task_state = ?1 where id = ?2", nativeQuery = true)
    @Modifying
    @Transactional
    void updateRwztById(Integer rwzt, Integer id);

    /**
     * 获取最早的任务截止时间和任务完成时间
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
     * 统计业务员 已接收、已处理案件数、应得佣金及实际佣金
     */
    @Query(value = "select count(id) from task where task_peopleId = ?1 and task_state = ?2", nativeQuery = true)
    int countAllByRwzxrAndRwzt(Integer rwzxr, Integer rwzt);

    @Query(value = "select sum(task_money) from task where task_peopleId = ?1 and task_state != '8'", nativeQuery = true)
    Double sumAllRwyjByRwzxr(Integer rwzxr);

    @Query(value = "select sum(actual_money) from task where task_peopleId = ?1 and task_state != '8'", nativeQuery = true)
    Double sumAllSjyjByRwzxr(Integer rwzxr);

    /**
     * 通过案件id、业务员id和任务状态统计业务员实际佣金
     */
    @Query(value = "SELECT case_id as ajid, sum(actual_money) as ajsjyj from task WHERE task_state = '6' and task_peopleId = ?1 GROUP BY case_id ORDER BY case_id", nativeQuery = true)
    List<Map<String, Double>> sumSjyjByAjid(Integer ywyid);

    @Query(value = "SELECT case_id as ajid, task_people as rwzxr, sum(actual_money) as ajsjyj from task WHERE task_state = '6' GROUP BY task_peopleId, case_id ORDER BY task_peopleId, case_id", nativeQuery = true)
    List<Map<String, Double>> sumAllSjyjByAjid();


    /**
     * 通过案件id查询任务是否都已完成
     */
    @Query(value = "select distinct id from task where case_id = ?1 and task_state in (1, 2, 3)", nativeQuery = true)
    List<Integer> findAllIdByAjidAndAjztIn(Integer ajid);


    /**
     * 财务人员确认已发放佣金，批量修改
     */
    @Query(value = "update task set task_state = '7' where id in ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void updateRwztByIdIn(List<Integer> ids);


    /**
     * 通过案件id统计任务佣金
     */
    @Query(value = "select sum(task_money) from task where case_id = ?1 and task_state != '8'", nativeQuery = true)
    Double sumAllRwyjByAjid(Integer ajid);

    /**
     * 通过任务执行人、任务状态查询
     */
    @Query(value = "select * from task where task_peopleId = ?1 and task_state = ?2 and case_id in (select distinct id from casebasemessage where show_state = '1') order by update_time desc ", nativeQuery = true)
    Page<TaskEntity> findAllByRwzxrAndRwztOrderByGxsjDesc(Integer ywyid, Integer rwzt, Pageable pageable);

    @Query(value = "select * from task where task_state in (1,2) and case_id in (select id from casebasemessage where show_state = '1' and area_id in (SELECT area_code from user where id = ?1)) order by task_state", nativeQuery = true)
    Page<TaskEntity> findAllByRwzxrOrderByRwztDesc(Integer ywyid, Pageable pageable);

    /**
     * 业务员单框查询
     */
    @Query(value = "SELECT * FROM task WHERE task_state in (1,2) and (CONCAT(task_name, task_time, task_money, task_description, task_way) LIKE ?1 or CONCAT(task_name, task_time, task_money, task_description, task_way) is null) and case_id in (SELECT id from casebasemessage where show_state = '1' and area_id in (SELECT area_code from user where id = ?2)) order by update_time desc ", nativeQuery = true)
    Page<TaskEntity> findAllByCondition(String param, Integer ywyid, Pageable pageable);

    /**
     * 多条件查询:任务名称、任务截止时间、任务方式、任务状态、任务执行人名称、任务完成时间
     */
    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "if(?5 is not null, task_state = ?5, 1 = 1) and " +
            "task_people like ?6 and " +
            "create_time between ?7 and ?8 and " +
            "case_id in (select distinct id from casebasemessage where show_state = '1')", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetweenAndRwcjsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                                                        Integer rwfs, Integer rwzt, String rwzxrmc,
                                                                        Date beginCjsj, Date endCjsj, Pageable pageable);


    @Query(value = "select * from task where task_name like ?1 and " +
            "task_time between ?2 and ?3 and " +
            "if(?4 is not null, task_way = ?4, 1 = 1) and " +
            "task_people like ?5 and  " +
            "complate_time between ?6 and ?7 and " +
            "create_time between ?8 and ?9 and task_state = '4' and " +
            "case_id in (select distinct id from casebasemessage where show_state = '1'", nativeQuery = true)
    Page<TaskEntity> findAllByConditionAndRwjzsjBetweenAAndRwcjsjBetweenAndRwcjsjBetween(String rwmc, Date beginJzsj, Date endJzsj,
                                                                                         Integer rwfs, String rwzxrmc, Date beginWcsj, Date endWcsj,
                                                                                         Date beginCjsj, Date endCjsj, Pageable pageable);
/**
 * @Desc: 用户案件完成情况统计分析,月度"
 * @param pageable
 */
    @Query(value = "SELECT id,name,group_concat(sl ORDER BY zt ASC) sl FROM (SELECT id,name,zt,sl FROM user LEFT JOIN (\n" +
            "                             SELECT uid,zt,sum(sl) sl FROM ( SELECT uid,entrycode zt,CASE WHEN zt = entrycode THEN sl ELSE 0 END  sl FROM (SELECT task_peopleId uid ,task_state zt,count(*) sl FROM task WHERE exists(SELECT 1 FROM user WHERE task_peopleId = user.id) and exists(SELECT 1 FROM casebasemessage WHERE case_id = casebasemessage.id AND show_state =1) GROUP BY  task_peopleId ,task_state)a ,dictionarylist WHERE dictid=9)udg GROUP BY uid,zt\n" +
            "                                         )se ON id = uid ORDER BY id ASC ,zt ASC)be GROUP BY id,name", nativeQuery = true, countQuery = "SELECT count(*) FROM user")
    Page<Map<String, Object>> peoplecount(Pageable pageable);

    /**
     * @param uid
     * @param begin
     * @param end
     * @Desc: 根据用户id以及时间段统计任务个状态数量
     */
    @Query(value = "SELECT task_state zt,count(*) sl FROM task WHERE task_peopleId = ?1 AND if(?2 IS NULL ,1=1,update_time >=?2)AND if(?3 IS NULL ,1=1,update_time <=?3) AND exists(SELECT 1 FROM casebasemessage WHERE case_id = casebasemessage.id AND show_state =1)GROUP BY task_peopleId,task_state", nativeQuery = true)
    List<Map<String, Object>> taskCountByUidAndDate(int uid, Date begin, Date end);

}
