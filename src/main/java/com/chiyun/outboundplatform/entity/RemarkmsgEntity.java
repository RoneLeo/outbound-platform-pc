package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "remarkmsg", schema = "ajjcxx", catalog = "")
public class RemarkmsgEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "显示状态 1-显示 2-不显示")
    private Integer xszt;

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
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "show_state")
    public Integer getXszt() {
        return xszt;
    }

    public void setXszt(Integer xszt) {
        this.xszt = xszt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemarkmsgEntity)) return false;
        RemarkmsgEntity that = (RemarkmsgEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(xszt, that.xszt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, remark, xszt);
    }
}
