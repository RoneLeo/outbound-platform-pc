package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "outboundmessage", schema = "ajjcxx", catalog = "")
public class OutboundmessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "外访省份")
    private String wfsf;
    @ApiModelProperty(value = "外访城市")
    private String wfcs;
    @ApiModelProperty(value = "外访区县")
    private String wfqx;
    @ApiModelProperty(value = "外访员")
    private String wfy;
    @ApiModelProperty(value = "外访员ID")
    private String wfyid;
    @ApiModelProperty(value = "外访原因")
    private String wfyy;
    @ApiModelProperty(value = "外访要求")
    private String wfyq;
    @ApiModelProperty(value = "外访期次")
    private Integer wfqc;
    @ApiModelProperty(value = "申请日期")
    private Date sqrq;
    @ApiModelProperty(value = "预计外访日期")
    private Date yjwfrq;
    @ApiModelProperty(value = "外访报告")
    private String wfbg;
    @ApiModelProperty(value = "外访备注")
    private String wfbz;

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
    @Column(name = "area1")
    public String getWfsf() {
        return wfsf;
    }

    public void setWfsf(String wfsf) {
        this.wfsf = wfsf;
    }

    @Basic
    @Column(name = "area2")
    public String getWfcs() {
        return wfcs;
    }

    public void setWfcs(String wfcs) {
        this.wfcs = wfcs;
    }

    @Basic
    @Column(name = "area3")
    public String getWfqx() {
        return wfqx;
    }

    public void setWfqx(String wfqx) {
        this.wfqx = wfqx;
    }

    @Basic
    @Column(name = "visitor")
    public String getWfy() {
        return wfy;
    }

    public void setWfy(String wfy) {
        this.wfy = wfy;
    }

    @Basic
    @Column(name = "visitorId")
    public String getWfyid() {
        return wfyid;
    }

    public void setWfyid(String wfyid) {
        this.wfyid = wfyid;
    }

    @Basic
    @Column(name = "reason")
    public String getWfyy() {
        return wfyy;
    }

    public void setWfyy(String wfyy) {
        this.wfyy = wfyy;
    }

    @Basic
    @Column(name = "request")
    public String getWfyq() {
        return wfyq;
    }

    public void setWfyq(String wfyq) {
        this.wfyq = wfyq;
    }

    @Basic
    @Column(name = "count")
    public Integer getWfqc() {
        return wfqc;
    }

    public void setWfqc(Integer wfqc) {
        this.wfqc = wfqc;
    }

    @Basic
    @Column(name = "appDate")
    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    @Basic
    @Column(name = "estDate")
    public Date getYjwfrq() {
        return yjwfrq;
    }

    public void setYjwfrq(Date yjwfrq) {
        this.yjwfrq = yjwfrq;
    }

    @Basic
    @Column(name = "report")
    public String getWfbg() {
        return wfbg;
    }

    public void setWfbg(String wfbg) {
        this.wfbg = wfbg;
    }

    @Basic
    @Column(name = "remark")
    public String getWfbz() {
        return wfbz;
    }

    public void setWfbz(String wfbz) {
        this.wfbz = wfbz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OutboundmessageEntity)) return false;
        OutboundmessageEntity that = (OutboundmessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(wfsf, that.wfsf) &&
                Objects.equals(wfcs, that.wfcs) &&
                Objects.equals(wfqx, that.wfqx) &&
                Objects.equals(wfy, that.wfy) &&
                Objects.equals(wfyid, that.wfyid) &&
                Objects.equals(wfyy, that.wfyy) &&
                Objects.equals(wfyq, that.wfyq) &&
                Objects.equals(wfqc, that.wfqc) &&
                Objects.equals(sqrq, that.sqrq) &&
                Objects.equals(yjwfrq, that.yjwfrq) &&
                Objects.equals(wfbg, that.wfbg) &&
                Objects.equals(wfbz, that.wfbz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, wfsf, wfcs, wfqx, wfy, wfyid, wfyy, wfyq, wfqc, sqrq, yjwfrq, wfbg, wfbz);
    }
}
