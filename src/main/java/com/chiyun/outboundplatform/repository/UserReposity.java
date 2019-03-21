package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserReposity extends CrudRepository<UserEntity, Long> {

    UserEntity findByYhmAndMm(String yhm, String mm);

    List<UserEntity> findAll();

//    List<UserEntity> findByJsAAndSzxzqdm(int js, String szxzqdm);

    UserEntity findByOpenid(String openid);

    UserEntity findBySqm(String sqm);
}
