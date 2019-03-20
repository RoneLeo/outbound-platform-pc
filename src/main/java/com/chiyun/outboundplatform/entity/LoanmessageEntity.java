package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "loanmessage", schema = "ajjcxx", catalog = "")
public class LoanmessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "委案日期")
    private Date warq;
    @ApiModelProperty(value = "贷款日期")
    private Date dkrq;
    @ApiModelProperty(value = "委案金额")
    private Double waje;
    @ApiModelProperty(value = "预计退案日")
    private Date yjtar;
    @ApiModelProperty(value = " 欠款")
    private Double qk;
    @ApiModelProperty(value = "最新欠款")
    private Double zxhk;
    @ApiModelProperty(value = "本金")
    private Double pcp;
    @ApiModelProperty(value = "利息")
    private Double lx;
    @ApiModelProperty(value = "滞纳金")
    private Double znj;
    @ApiModelProperty(value = "已还款")
    private Double yhk;
    @ApiModelProperty(value = "最低还款")
    private Double zdhk;


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
    @Column(name = "caseDate")
    public Date getWarq() {
        return warq;
    }

    public void setWarq(Date warq) {
        this.warq = warq;
    }

    @Basic
    @Column(name = "loanDate")
    public Date getDkrq() {
        return dkrq;
    }

    public void setDkrq(Date dkrq) {
        this.dkrq = dkrq;
    }

    @Basic
    @Column(name = "caseAmt")
    public Double getWaje() {
        return waje;
    }

    public void setWaje(Double waje) {
        this.waje = waje;
    }

    @Basic
    @Column(name = "backDate")
    public Date getYjtar() {
        return yjtar;
    }

    public void setYjtar(Date yjtar) {
        this.yjtar = yjtar;
    }

    @Basic
    @Column(name = "debt")
    public Double getQk() {
        return qk;
    }

    public void setQk(Double qk) {
        this.qk = qk;
    }

    @Basic
    @Column(name = "lastDebt")
    public Double getZxhk() {
        return zxhk;
    }

    public void setZxhk(Double zxhk) {
        this.zxhk = zxhk;
    }

    @Basic
    @Column(name = "pcp")
    public Double getPcp() {
        return pcp;
    }

    public void setPcp(Double pcp) {
        this.pcp = pcp;
    }

    @Basic
    @Column(name = "interest")
    public Double getLx() {
        return lx;
    }

    public void setLx(Double lx) {
        this.lx = lx;
    }

    @Basic
    @Column(name = "overPaid")
    public Double getZnj() {
        return znj;
    }

    public void setZnj(Double znj) {
        this.znj = znj;
    }

    @Basic
    @Column(name = "casePaid")
    public Double getYhk() {
        return yhk;
    }

    public void setYhk(Double yhk) {
        this.yhk = yhk;
    }

    @Basic
    @Column(name = "minPaid")
    public Double getZdhk() {
        return zdhk;
    }

    public void setZdhk(Double zdhk) {
        this.zdhk = zdhk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanmessageEntity)) return false;
        LoanmessageEntity that = (LoanmessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(warq, that.warq) &&
                Objects.equals(dkrq, that.dkrq) &&
                Objects.equals(waje, that.waje) &&
                Objects.equals(yjtar, that.yjtar) &&
                Objects.equals(qk, that.qk) &&
                Objects.equals(zxhk, that.zxhk) &&
                Objects.equals(pcp, that.pcp) &&
                Objects.equals(lx, that.lx) &&
                Objects.equals(znj, that.znj) &&
                Objects.equals(yhk, that.yhk) &&
                Objects.equals(zdhk, that.zdhk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, warq, dkrq, waje, yjtar, qk, zxhk, pcp, lx, znj, yhk, zdhk);
    }
}
