package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "casebasemessage", schema = "ajjcxx", catalog = "")
public class CasebasemessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "个案序列号")
    private String gaxlh;
    @ApiModelProperty(value = "案件名称")
    private String ajmc;
    @ApiModelProperty(value = "案件类型")
    private Integer ajlx;
    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;
    @ApiModelProperty(value = "案件区域")
    private Integer ajqy;
    @ApiModelProperty(value = "案件佣金")
    private Double ajyj;
    @ApiModelProperty(value = "导入时间")
    private Date drsj;



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
    @Column(name = "casename")
    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
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
    @Column(name = "case_type")
    public Integer getAjlx() {
        return ajlx;
    }

    public void setAjlx(Integer ajlx) {
        this.ajlx = ajlx;
    }

    @Basic
    @Column(name = "case_state")
    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    @Basic
    @Column(name = "area_id")
    public Integer getAjqy() {
        return ajqy;
    }

    public void setAjqy(Integer ajqy) {
        this.ajqy = ajqy;
    }

    @Basic
    @Column(name = "case_pay")
    public Double getAjyj() {
        return ajyj;
    }

    public void setAjyj(Double ajyj) {
        this.ajyj = ajyj;
    }

    @Basic
    @Column(name = "import_time")
    public Date getDrsj() {
        return drsj;
    }

    public void setDrsj(Date drsj) {
        this.drsj = drsj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasebasemessageEntity)) return false;
        CasebasemessageEntity that = (CasebasemessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(gaxlh, that.gaxlh) &&
                Objects.equals(ajmc, that.ajmc) &&
                Objects.equals(ajlx, that.ajlx) &&
                Objects.equals(ajzt, that.ajzt) &&
                Objects.equals(ajqy, that.ajqy) &&
                Objects.equals(ajyj, that.ajyj) &&
                Objects.equals(drsj, that.drsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, gaxlh, ajmc, ajlx, ajzt, ajqy, ajyj, drsj);
    }
}
