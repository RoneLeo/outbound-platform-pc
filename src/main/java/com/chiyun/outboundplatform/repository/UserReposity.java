package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserReposity extends CrudRepository<UserEntity, Long> {

    UserEntity findByYhmAndMm(String yhm, String mm);

    List<UserEntity> findAll();

    @Query(value = "select * from user where role_id in ?1 and area_code = ?2 ", nativeQuery = true)
    List<UserEntity> findByJsAndSzxzqdm(int[] js, String szxzqdm);

    UserEntity findByOpenid(String openid);

    UserEntity findBySqm(String sqm);

    @Query(value = "delete from user where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    UserEntity findById(int id);
}
