package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.ColltypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CollTypeRepository extends JpaRepository<ColltypeEntity, Integer> {

}
