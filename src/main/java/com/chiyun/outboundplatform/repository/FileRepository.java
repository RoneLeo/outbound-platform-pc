package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    @Query(value = "select group_concat(id) from file where task_id = ?1", nativeQuery = true)
    String findAllIdByRwid(Integer rwid);

    List<FileEntity> findAllByRwid(Integer rwid);

    void deleteAllByRwid(Integer rwid);

}
