package com.chiyun.outboundplatform.repository;

import com.chiyun.outboundplatform.entity.DictionaryEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DictionaryRepository extends CrudRepository<DictionaryEntity, Long> {

    /**
     * 通过主键来查询
     * @param id
     * @return
     */
     public DictionaryEntity findById(Integer id);

    /**
     *   通过字典中文名来查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where name like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryEntity>  findByName(String zdzwmc);
    /**
     *   通过字典中文名和字典状态赛选来查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and state=?2", nativeQuery = true)
    List<DictionaryEntity>  findByNameAndState(String zdzwmc,String state);

    /**
     *   通过字典英文名查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where eng_name like  concat('%',?1,'%') ", nativeQuery = true)
    List<DictionaryEntity>  findByEng_Name(String zdywmc);
    /**
     *   通过字典英文名和字典状态赛选查询字典
     * @param
     * @return
     */
    @Query(value = "select * from dictionary where eng_name like  concat('%',?1,'%')  and static=?2", nativeQuery = true)
    List<DictionaryEntity>  findByEng_NameAndState(String zdywmc,String state);

    @Query(value = "select * from dictionary where name =?1  and eng_name=?2 ", nativeQuery = true)
    List<DictionaryEntity>  findDictByZdzwmAndZdywm(String zdzwmc,String zdywmc);


    @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and eng_name  like  concat('%',?2,'%') ", nativeQuery = true)
    List<DictionaryEntity>  findDictByNameAndEng_Name(String zdzwmc,String zdywmc);


    @Query(value = "select * from dictionary where name like  concat('%',?1,'%')  and eng_name like  concat('%',?2,'%')  and state=?3", nativeQuery = true)
    List<DictionaryEntity> findDictByNameAndEng_NameAndState (String name,String eng_name ,String state);


    /**
     * 查询所有的数据
     * @return
     */
    List<DictionaryEntity> findAll();
    /**
     * 查询所有的数据
     * @return
     */
    @Query(value = "select * from dictionary where state=?1",nativeQuery = true)
    List<DictionaryEntity> findAllByState(String State);

    /**
     *  新增和修改都能执行
     * @param entity
     * @return
     */

     DictionaryEntity save(DictionaryEntity entity);



    /**
     *  通过id删除
     */
    @Query(value = "delete from dictionary where id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteById(Integer id);



    /**
     *  注销字典
     * @param id
     * @return
     */
    @Query(value = "update dictionary  dic set dic.state='1' where dic.id=?1 and dic.state='0'" , nativeQuery = true)
    @Modifying
    @Transactional
     int   cancellationById(Integer id);

    @Query(value = "update dictionary  dic set dic.state='0' where dic.id=?1 and dic.state='1'" , nativeQuery = true)
    @Modifying
    @Transactional
     int   unCancellationById(Integer id);

    Boolean existsById(Integer id);

}
