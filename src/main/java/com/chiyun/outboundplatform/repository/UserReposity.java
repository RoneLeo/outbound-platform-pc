package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserReposity extends CrudRepository<UserEntity, Long> {

    UserEntity findByYhmAndMm(String yhm, String mm);

//    List<UserEntity> findAll();

    //Page<UserEntity> findAllByOrderByCjsjDesc(Pageable pageable);
    Page<UserEntity> findByZtIn(List<Integer> ztlist, Pageable pageable);


   // @Query(value = "select * from user where role_id in ?1 and area_code = ?2  order by create_time desc", nativeQuery = true)
    //Page<UserEntity> findByJsInAndSzxzqdm(List<Integer> js, String szxzqdm, Pageable pageable);
   //Page<UserEntity> findByJsInAndSzxzqdm(int lx, List<String> ztList, int[] js, String szxzqdm, Pageable pageable);

    Page<UserEntity> findByJsInAndZtInAndSzxzqdm(int[] js, List<Integer> ztList, String szxzqdm, Pageable pageable);


    UserEntity findByOpenid(String openid);

    UserEntity findBySqm(String sqm);

    @Query(value = "delete from user where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);

    UserEntity findById(int id);

    UserEntity findByYhm(String yhm);

    List<UserEntity> findByJsInAndMzLikeAndSzxzqdm(int js[], String mz, String szxzqdm);
}
