package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "batch", schema = "ajjcxx", catalog = "")
public class BatchEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "字段中文")
    private String zdzwmc;
    @ApiModelProperty(value = "字段英文")
    private String zdywmc;
    @ApiModelProperty(value = "排序")
    private Integer sort;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "case_id")
    public Integer getAjid() {
        return ajid;
    }

    public void setAjid(Integer ajid) {
        this.ajid = ajid;
    }

    @Basic
    @Column(name = "fieldCname")
    public String getZdzwmc() {
        return zdzwmc;
    }

    public void setZdzwmc(String zdzwmc) {
        this.zdzwmc = zdzwmc;
    }

    @Basic
    @Column(name = "fieldEname")
    public String getZdywmc() {
        return zdywmc;
    }

    public void setZdywmc(String zdywmc) {
        this.zdywmc = zdywmc;
    }

    @Basic
    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchEntity that = (BatchEntity) o;
        return id == that.id &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(zdzwmc, that.zdzwmc) &&
                Objects.equals(zdywmc, that.zdywmc) &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, zdzwmc, zdywmc, sort);
    }
}
