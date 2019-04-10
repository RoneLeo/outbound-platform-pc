package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.LogEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LogRepository extends CrudRepository<LogEntity, Long> {

    LogEntity findById(int id);

    @Query(value = "delete from log where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(int id);

    @Query(value = "select * from log where user_name like ?1 and event like ?2", nativeQuery = true)
    Page<LogEntity> findByCzrAndCzsj(String czr, String czsj, Pageable pageable);

    @Query(value = "select * from log where user_name like ?1 and event like ?2 and create_time < ?3", nativeQuery = true)
    Page<LogEntity> findByCzrAndCzsjAndJssj(String czr, String czsj, Date jssj, Pageable pageable);

    @Query(value = "select * from log where user_name like ?1 and event like ?2 and create_time > ?3", nativeQuery = true)
    Page<LogEntity> findByCzrAndCzsjAndKssj(String czr, String czsj, Date kssj, Pageable pageable);

    @Query(value = "select * from log where user_name like ?1 and event like ?2 and create_time between ?3 and ?4", nativeQuery = true)
    Page<LogEntity> findByCzrAndCzsjAndKssjAndJssj(String czr, String czsj, Date kssj, Date jssj, Pageable pageable);

    @Query(value = "SELECT user_id uid,user_name xm, count(*) sl FROM log WHERE create_time BETWEEN ?1 AND ?2 AND exists(SELECT 1 FROM user WHERE user_id = user.id AND type = 1 AND role_id >=3 AND role_id<=4) GROUP BY user_id ORDER BY sl DESC", nativeQuery = true)
    List<Map<String, Object>> userActive(Date begin, Date end);
}
