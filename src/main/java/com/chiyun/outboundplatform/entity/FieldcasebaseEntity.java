package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "fieldcasebase", schema = "ajjcxx", catalog = "")
public class FieldcasebaseEntity {
    private Integer id;
    @ApiModelProperty(value = "字段中文")
    private String zdzwmc;
    @ApiModelProperty(value = "字段英文")
    private String zdywmc;
    @ApiModelProperty(value = "基础信息类型")
    private Integer jcxxlx;

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
    @Column(name = "basetype")
    public Integer getJcxxlx() {
        return jcxxlx;
    }

    public void setJcxxlx(Integer jcxxlx) {
        this.jcxxlx = jcxxlx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FieldcasebaseEntity that = (FieldcasebaseEntity) o;
        return id == that.id &&
                Objects.equals(zdzwmc, that.zdzwmc) &&
                Objects.equals(zdywmc, that.zdywmc) &&
                Objects.equals(jcxxlx, that.jcxxlx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, zdzwmc, zdywmc, jcxxlx);
    }
}
