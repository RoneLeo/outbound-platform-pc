package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.RoleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleReposity extends CrudRepository<RoleEntity, Long> {

    List<RoleEntity> findAll();

    @Query(value = "delete from role where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

//    @Query(value = "delete from role where id = ?1", nativeQuery = true)
//    @Modifying
//    @Transactional
//    int update(String id);
}
