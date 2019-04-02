package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "casebasemessage", schema = "ajjcxx", catalog = "")
public class CasebasemessageAllEntity {
    private Integer id;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "个案序列号")
    private String gaxlh;
    @ApiModelProperty(value = "批次号")
    private String pch;
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
    @ApiModelProperty(value = "显示状态 1-显示 2-不显示")
    private Integer xszt;
    @ApiModelProperty(value = "卡号信息")
    private List<CardmessageEntity> khxx;
    @ApiModelProperty(value = "催收员信息")
    private List<EmpmessageEntity> csyxx;
    @ApiModelProperty(value = "贷款信息")
    private List<LoanmessageEntity> dkxx;
    @ApiModelProperty(value = "对象信息")
    private List<UsermessageEntity> dxxx;
    @ApiModelProperty(value = "外访信息")
    private List<OutboundmessageEntity> wfxx;
    @ApiModelProperty(value = "案人信息")
    private List<CasepeoplemessageEntity> arxx;
    @ApiModelProperty(value = "其他信息")
    private List<OthermessageEntity> qtxx;
    @ApiModelProperty(value = "联系人信息")
    private List<LinkmanmessageEntity> lxrxx;
    @ApiModelProperty(value = "备注信息")
    private List<RemarkmsgEntity> bzxx;



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
    @Column(name = "case_name")
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
    @Column(name = "batNo")
    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch;
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

    @Basic
    @Column(name = "caseNo")
    public String getGaxlh() {
        return gaxlh;
    }

    public void setGaxlh(String gaxlh) {
        this.gaxlh = gaxlh;
    }

    @Basic
    @Column(name = "show_state")
    public Integer getXszt() {
        return xszt;
    }

    public void setXszt(Integer xszt) {
        this.xszt = xszt;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<CardmessageEntity> getKhxx() {
        return khxx;
    }

    public void setKhxx(List<CardmessageEntity> khxx) {
        this.khxx = khxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<EmpmessageEntity> getCsyxx() {
        return csyxx;
    }

    public void setCsyxx(List<EmpmessageEntity> csyxx) {
        this.csyxx = csyxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<LoanmessageEntity> getDkxx() {
        return dkxx;
    }

    public void setDkxx(List<LoanmessageEntity> dkxx) {
        this.dkxx = dkxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<UsermessageEntity> getDxxx() {
        return dxxx;
    }

    public void setDxxx(List<UsermessageEntity> dxxx) {
        this.dxxx = dxxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<OutboundmessageEntity> getWfxx() {
        return wfxx;
    }

    public void setWfxx(List<OutboundmessageEntity> wfxx) {
        this.wfxx = wfxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<CasepeoplemessageEntity> getArxx() {
        return arxx;
    }

    public void setArxx(List<CasepeoplemessageEntity> arxx) {
        this.arxx = arxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<OthermessageEntity> getQtxx() {
        return qtxx;
    }

    public void setQtxx(List<OthermessageEntity> qtxx) {
        this.qtxx = qtxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<LinkmanmessageEntity> getLxrxx() {
        return lxrxx;
    }

    public void setLxrxx(List<LinkmanmessageEntity> lxrxx) {
        this.lxrxx = lxrxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="case_id")
    public List<RemarkmsgEntity> getBzxx() {
        return bzxx;
    }

    public void setBzxx(List<RemarkmsgEntity> bzxx) {
        this.bzxx = bzxx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasebasemessageAllEntity)) return false;
        CasebasemessageAllEntity that = (CasebasemessageAllEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(gaxlh, that.gaxlh) &&
                Objects.equals(pch, that.pch) &&
                Objects.equals(ajmc, that.ajmc) &&
                Objects.equals(ajlx, that.ajlx) &&
                Objects.equals(ajzt, that.ajzt) &&
                Objects.equals(ajqy, that.ajqy) &&
                Objects.equals(ajyj, that.ajyj) &&
                Objects.equals(drsj, that.drsj) &&
                Objects.equals(xszt, that.xszt) &&
                Objects.equals(khxx, that.khxx) &&
                Objects.equals(csyxx, that.csyxx) &&
                Objects.equals(dkxx, that.dkxx) &&
                Objects.equals(dxxx, that.dxxx) &&
                Objects.equals(wfxx, that.wfxx) &&
                Objects.equals(arxx, that.arxx) &&
                Objects.equals(qtxx, that.qtxx) &&
                Objects.equals(lxrxx, that.lxrxx) &&
                Objects.equals(bzxx, that.bzxx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pcid, gaxlh, pch, ajmc, ajlx, ajzt, ajqy, ajyj, drsj, xszt, khxx, csyxx, dkxx, dxxx, wfxx, arxx, qtxx, lxrxx, bzxx);
    }
}
