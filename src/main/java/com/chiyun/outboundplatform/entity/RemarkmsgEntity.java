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
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "基础信息id")
    private Integer jcxxid;
    @ApiModelProperty(value = "备注")
    private String remark;

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
    @Column(name = "batch_id")
    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    @Basic
    @Column(name = "casebase_id")
    public Integer getJcxxid() {
        return jcxxid;
    }

    public void setJcxxid(Integer jcxxid) {
        this.jcxxid = jcxxid;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RemarkmsgEntity)) return false;
        RemarkmsgEntity that = (RemarkmsgEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(jcxxid, that.jcxxid) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, jcxxid, remark);
    }
}
