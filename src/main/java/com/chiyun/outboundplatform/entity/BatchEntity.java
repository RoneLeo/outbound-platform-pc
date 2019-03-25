package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "batch", schema = "ajjcxx", catalog = "")
public class BatchEntity {
    private Integer id;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "批次名称")
    private String pcmc;
    @ApiModelProperty(value = "基础字段id")
    private String jczdid;
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
    public String getJczdid() {
        return jczdid;
    }

    public void setJczdid(String jczdid) {
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

    @Basic
    @Column(name = "batch_name")
    public String getPcmc() {
        return pcmc;
    }

    public void setPcmc(String pcmc) {
        this.pcmc = pcmc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatchEntity)) return false;
        BatchEntity entity = (BatchEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(pcid, entity.pcid) &&
                Objects.equals(jczdid, entity.jczdid) &&
                Objects.equals(zdzwmc, entity.zdzwmc) &&
                Objects.equals(zdywmc, entity.zdywmc) &&
                Objects.equals(sort, entity.sort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pcid, jczdid, zdzwmc, zdywmc, sort);
    }
}
