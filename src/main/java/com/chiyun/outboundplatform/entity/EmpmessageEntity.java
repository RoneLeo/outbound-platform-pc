package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "empmessage", schema = "ajjcxx", catalog = "")
public class EmpmessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "催收员办公电话")
    private String csybgdh;
    @ApiModelProperty(value = "催收员手机")
    private String csysj;
    @ApiModelProperty(value = "催收员姓名")
    private String csyxm;
    @ApiModelProperty(value = "催收员工号")
    private String csygh;
    @ApiModelProperty(value = "催收部门")
    private String csbm;

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
    @Column(name = "empPhone")
    public String getCsybgdh() {
        return csybgdh;
    }

    public void setCsybgdh(String csybgdh) {
        this.csybgdh = csybgdh;
    }

    @Basic
    @Column(name = "empMob")
    public String getCsysj() {
        return csysj;
    }

    public void setCsysj(String csysj) {
        this.csysj = csysj;
    }

    @Basic
    @Column(name = "caseUser")
    public String getCsyxm() {
        return csyxm;
    }

    public void setCsyxm(String csyxm) {
        this.csyxm = csyxm;
    }

    @Basic
    @Column(name = "empNo")
    public String getCsygh() {
        return csygh;
    }

    public void setCsygh(String csygh) {
        this.csygh = csygh;
    }

    @Basic
    @Column(name = "org")
    public String getCsbm() {
        return csbm;
    }

    public void setCsbm(String csbm) {
        this.csbm = csbm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmpmessageEntity)) return false;
        EmpmessageEntity that = (EmpmessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(csybgdh, that.csybgdh) &&
                Objects.equals(csysj, that.csysj) &&
                Objects.equals(csyxm, that.csyxm) &&
                Objects.equals(csygh, that.csygh) &&
                Objects.equals(csbm, that.csbm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, csybgdh, csysj, csyxm, csygh, csbm);
    }
}
