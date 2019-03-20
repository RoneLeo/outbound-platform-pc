package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "othermessage", schema = "ajjcxx", catalog = "")
public class OthermessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "逾期账龄")
    private String yqzl;
    @ApiModelProperty(value = "还款期限")
    private Date hkqx;
    @ApiModelProperty(value = "信用额度")
    private String xyed;
    @ApiModelProperty(value = "信贷分类")
    private Integer xdfl;
    @ApiModelProperty(value = "信贷分类名称")
    private String xdflmc;
    @ApiModelProperty(value = "催收分类")
    private Integer cslx;
    @ApiModelProperty(value = "催收分类名称")
    private String cslxmc;
    @ApiModelProperty(value = "拖欠级别")
    private Integer tqjb;
    @ApiModelProperty(value = "拖欠级别名称")
    private String tqjbmc;
    @ApiModelProperty(value = "保证金")
    private Double bzj;
    @ApiModelProperty(value = "开卡日")
    private Date kkr;
    @ApiModelProperty(value = "最后还款日")
    private Date zhhkr;
    @ApiModelProperty(value = "最后还款额")
    private Double zhhke;
    @ApiModelProperty(value = "最后消费日")
    private Date zhxfr;
    @ApiModelProperty(value = "最后提现日")
    private Date zhtxr;
    @ApiModelProperty(value = "停卡日")
    private Date tkr;
    @ApiModelProperty(value = "账单日")
    private Date zdr;
    @ApiModelProperty(value = "人民币")
    private Double rmb;
    @ApiModelProperty(value = "外币")
    private Double usd;
    @ApiModelProperty(value = "港币")
    private Double gb;
    @ApiModelProperty(value = "委案期数")
    private Integer waqs;
    @ApiModelProperty(value = "逾期天数")
    private Integer yqts;
    @ApiModelProperty(value = "剩余本金")
    private Double sybj;
    @ApiModelProperty(value = "逾期期数")
    private Integer yqqs;
    @ApiModelProperty(value = "已还期数")
    private Integer yhqs;
    @ApiModelProperty(value = "商品")
    private String sp;
    @ApiModelProperty(value = "商户")
    private String sh;

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
    @Column(name = "aged")
    public String getYqzl() {
        return yqzl;
    }

    public void setYqzl(String yqzl) {
        this.yqzl = yqzl;
    }

    @Basic
    @Column(name = "payDL")
    public Date getHkqx() {
        return hkqx;
    }

    public void setHkqx(Date hkqx) {
        this.hkqx = hkqx;
    }

    @Basic
    @Column(name = "credLim")
    public String getXyed() {
        return xyed;
    }

    public void setXyed(String xyed) {
        this.xyed = xyed;
    }

    @Basic
    @Column(name = "loanType")
    public Integer getXdfl() {
        return xdfl;
    }

    public void setXdfl(Integer xdfl) {
        this.xdfl = xdfl;
    }

    @Basic
    @Column(name = "loanTypeName")
    public String getXdflmc() {
        return xdflmc;
    }

    public void setXdflmc(String xdflmc) {
        this.xdflmc = xdflmc;
    }

    @Basic
    @Column(name = "collType")
    public Integer getCslx() {
        return cslx;
    }
    public void setCslx(Integer cslx) {
        this.cslx = cslx;
    }

    @Basic
    @Column(name = "collTypeName")
    public String getCslxmc() {
        return cslxmc;
    }

    public void setCslxmc(String cslxmc) {
        this.cslxmc = cslxmc;
    }

    @Basic
    @Column(name = "delayLv")
    public Integer getTqjb() {
        return tqjb;
    }

    public void setTqjb(Integer tqjb) {
        this.tqjb = tqjb;
    }

    @Basic
    @Column(name = "delayLvName")
    public String getTqjbmc() {
        return tqjbmc;
    }

    public void setTqjbmc(String tqjbmc) {
        this.tqjbmc = tqjbmc;
    }

    @Basic
    @Column(name = "guaM")
    public Double getBzj() {
        return bzj;
    }

    public void setBzj(Double bzj) {
        this.bzj = bzj;
    }

    @Basic
    @Column(name = "creDate")
    public Date getKkr() {
        return kkr;
    }

    public void setKkr(Date kkr) {
        this.kkr = kkr;
    }

    @Basic
    @Column(name = "paidDate")
    public Date getZhhkr() {
        return zhhkr;
    }

    public void setZhhkr(Date zhhkr) {
        this.zhhkr = zhhkr;
    }

    @Basic
    @Column(name = "lastPaid")
    public Double getZhhke() {
        return zhhke;
    }

    public void setZhhke(Double zhhke) {
        this.zhhke = zhhke;
    }

    @Basic
    @Column(name = "conDate")
    public Date getZhxfr() {
        return zhxfr;
    }

    public void setZhxfr(Date zhxfr) {
        this.zhxfr = zhxfr;
    }

    @Basic
    @Column(name = "raiDate")
    public Date getZhtxr() {
        return zhtxr;
    }

    public void setZhtxr(Date zhtxr) {
        this.zhtxr = zhtxr;
    }

    @Basic
    @Column(name = "stopDate")
    public Date getTkr() {
        return tkr;
    }

    public void setTkr(Date tkr) {
        this.tkr = tkr;
    }

    @Basic
    @Column(name = "billDate")
    public Date getZdr() {
        return zdr;
    }

    public void setZdr(Date zdr) {
        this.zdr = zdr;
    }

    @Basic
    @Column(name = "rmb")
    public Double getRmb() {
        return rmb;
    }

    public void setRmb(Double rmb) {
        this.rmb = rmb;
    }

    @Basic
    @Column(name = "usd")
    public Double getUsd() {
        return usd;
    }

    public void setUsd(Double usd) {
        this.usd = usd;
    }

    @Basic
    @Column(name = "hkd")
    public Double getGb() {
        return gb;
    }

    public void setGb(Double gb) {
        this.gb = gb;
    }

    @Basic
    @Column(name = "casCount")
    public Integer getWaqs() {
        return waqs;
    }

    public void setWaqs(Integer waqs) {
        this.waqs = waqs;
    }

    @Basic
    @Column(name = "overdueDays")
    public Integer getYqts() {
        return yqts;
    }

    public void setYqts(Integer yqts) {
        this.yqts = yqts;
    }

    @Basic
    @Column(name = "leftPri")
    public Double getSybj() {
        return sybj;
    }

    public void setSybj(Double sybj) {
        this.sybj = sybj;
    }

    @Basic
    @Column(name = "overdueNum")
    public Integer getYqqs() {
        return yqqs;
    }

    public void setYqqs(Integer yqqs) {
        this.yqqs = yqqs;
    }

    @Basic
    @Column(name = "paidCount")
    public Integer getYhqs() {
        return yhqs;
    }

    public void setYhqs(Integer yhqs) {
        this.yhqs = yhqs;
    }

    @Basic
    @Column(name = "prod")
    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    @Basic
    @Column(name = "saler")
    public String getSh() {
        return sh;
    }

    public void setSh(String sh) {
        this.sh = sh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OthermessageEntity)) return false;
        OthermessageEntity that = (OthermessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(yqzl, that.yqzl) &&
                Objects.equals(hkqx, that.hkqx) &&
                Objects.equals(xyed, that.xyed) &&
                Objects.equals(xdfl, that.xdfl) &&
                Objects.equals(xdflmc, that.xdflmc) &&
                Objects.equals(cslx, that.cslx) &&
                Objects.equals(cslxmc, that.cslxmc) &&
                Objects.equals(tqjb, that.tqjb) &&
                Objects.equals(tqjbmc, that.tqjbmc) &&
                Objects.equals(bzj, that.bzj) &&
                Objects.equals(kkr, that.kkr) &&
                Objects.equals(zhhkr, that.zhhkr) &&
                Objects.equals(zhhke, that.zhhke) &&
                Objects.equals(zhxfr, that.zhxfr) &&
                Objects.equals(zhtxr, that.zhtxr) &&
                Objects.equals(tkr, that.tkr) &&
                Objects.equals(zdr, that.zdr) &&
                Objects.equals(rmb, that.rmb) &&
                Objects.equals(usd, that.usd) &&
                Objects.equals(gb, that.gb) &&
                Objects.equals(waqs, that.waqs) &&
                Objects.equals(yqts, that.yqts) &&
                Objects.equals(sybj, that.sybj) &&
                Objects.equals(yqqs, that.yqqs) &&
                Objects.equals(yhqs, that.yhqs) &&
                Objects.equals(sp, that.sp) &&
                Objects.equals(sh, that.sh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, yqzl, hkqx, xyed, xdfl, xdflmc, cslx, cslxmc, tqjb, tqjbmc, bzj, kkr, zhhkr, zhhke, zhxfr, zhtxr, tkr, zdr, rmb, usd, gb, waqs, yqts, sybj, yqqs, yhqs, sp, sh);
    }
}
