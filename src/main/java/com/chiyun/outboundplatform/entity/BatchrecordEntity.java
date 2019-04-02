package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "batchrecord", schema = "ajjcxx", catalog = "")
public class BatchrecordEntity {
    private Integer id;
    @ApiModelProperty(value = "模板id")
    private String pcid;
    @ApiModelProperty(value = "基础字段id")
    private Integer jczdid;
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
    @Column(name = "batch_id")
    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    @Basic
    @Column(name = "fieldcasebase_id")
    public Integer getJczdid() {
        return jczdid;
    }

    public void setJczdid(Integer jczdid) {
        this.jczdid = jczdid;
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
        if (!(o instanceof BatchrecordEntity)) return false;
        BatchrecordEntity that = (BatchrecordEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(jczdid, that.jczdid) &&
                Objects.equals(zdzwmc, that.zdzwmc) &&
                Objects.equals(zdywmc, that.zdywmc) &&
                Objects.equals(sort, that.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pcid, jczdid, zdzwmc, zdywmc, sort);
    }
}
