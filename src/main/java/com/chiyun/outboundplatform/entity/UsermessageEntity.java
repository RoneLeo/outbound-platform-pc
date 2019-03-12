package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "usermessage", schema = "ajjcxx", catalog = "")
public class UsermessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "对象姓名")
    private String dxxm;
    @ApiModelProperty(value = "对象年龄")
    private Integer dxnl;
    @ApiModelProperty(value = "对象性别")
    private Integer dxxb;
    @ApiModelProperty(value = "地址")
    private String dxdz;
    @ApiModelProperty(value = "地址类型")
    private String dzlx;

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
    @Column(name = "name")
    public String getDxxm() {
        return dxxm;
    }

    public void setDxxm(String dxxm) {
        this.dxxm = dxxm;
    }

    @Basic
    @Column(name = "age")
    public Integer getDxnl() {
        return dxnl;
    }

    public void setDxnl(Integer dxnl) {
        this.dxnl = dxnl;
    }

    @Basic
    @Column(name = "sex")
    public Integer getDxxb() {
        return dxxb;
    }

    public void setDxxb(Integer dxxb) {
        this.dxxb = dxxb;
    }

    @Basic
    @Column(name = "add")
    public String getDxdz() {
        return dxdz;
    }

    public void setDxdz(String dxdz) {
        this.dxdz = dxdz;
    }

    @Basic
    @Column(name = "addType")
    public String getDzlx() {
        return dzlx;
    }

    public void setDzlx(String dzlx) {
        this.dzlx = dzlx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsermessageEntity that = (UsermessageEntity) o;
        return id == that.id &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(dxxm, that.dxxm) &&
                Objects.equals(dxnl, that.dxnl) &&
                Objects.equals(dxxb, that.dxxb) &&
                Objects.equals(dxdz, that.dxdz) &&
                Objects.equals(dzlx, that.dzlx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, dxxm, dxnl, dxxb, dxdz, dzlx);
    }
}
