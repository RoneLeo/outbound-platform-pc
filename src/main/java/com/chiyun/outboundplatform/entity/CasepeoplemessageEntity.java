package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "casepeoplemessage", schema = "ajjcxx", catalog = "")
public class CasepeoplemessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "委托方名称")
    private String wtfmc;
    @ApiModelProperty(value = "案人名称")
    private String armc;
    @ApiModelProperty(value = "案人性别")
    private String arxb;
    @ApiModelProperty(value = "单位名称")
    private String dwmc;
    @ApiModelProperty(value = "单位地址")
    private String dwdz;
    @ApiModelProperty(value = "案人部门")
    private String arbm;
    @ApiModelProperty(value = "案人职位")
    private String arzw;
    @ApiModelProperty(value = "家庭地址")
    private String jtdz;
    @ApiModelProperty(value = "户籍地址")
    private String hjdz;
    @ApiModelProperty(value = "对账单地址")
    private String dzddz;
    @ApiModelProperty(value = "案人手机")
    private String arsj;
    @ApiModelProperty(value = "案人宅电")
    private String arzd;
    @ApiModelProperty(value = "案人单位电话")
    private String ardwdh;
    @ApiModelProperty(value = "案人证件号")
    private String arzjh;
    @ApiModelProperty(value = "案人电邮")
    private String ardy;
    @ApiModelProperty(value = "案人证件号(隐藏后四位)")
    private String arzjhychsw;
    @ApiModelProperty(value = "案人证件号(隐藏中四位)")
    private String arzjhyczsw;
    @ApiModelProperty(value = "案人证件号(隐藏中六位)")
    private String arzjhyczlw;

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
    @Column(name = "bankName")
    public String getWtfmc() {
        return wtfmc;
    }

    public void setWtfmc(String wtfmc) {
        this.wtfmc = wtfmc;
    }
    @Basic
    @Column(name = "cName")
    public String getArmc() {
        return armc;
    }

    public void setArmc(String armc) {
        this.armc = armc;
    }

    @Basic
    @Column(name = "cSex")
    public String getArxb() {
        return arxb;
    }

    public void setArxb(String arxb) {
        this.arxb = arxb;
    }

    @Basic
    @Column(name = "company")
    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    @Basic
    @Column(name = "comAddr")
    public String getDwdz() {
        return dwdz;
    }

    public void setDwdz(String dwdz) {
        this.dwdz = dwdz;
    }

    @Basic
    @Column(name = "part")
    public String getArbm() {
        return arbm;
    }

    public void setArbm(String arbm) {
        this.arbm = arbm;
    }

    @Basic
    @Column(name = "pos")
    public String getArzw() {
        return arzw;
    }

    public void setArzw(String arzw) {
        this.arzw = arzw;
    }

    @Basic
    @Column(name = "homeAddr")
    public String getJtdz() {
        return jtdz;
    }

    public void setJtdz(String jtdz) {
        this.jtdz = jtdz;
    }

    @Basic
    @Column(name = "regAddr")
    public String getHjdz() {
        return hjdz;
    }

    public void setHjdz(String hjdz) {
        this.hjdz = hjdz;
    }

    @Basic
    @Column(name = "mailAddr")
    public String getDzddz() {
        return dzddz;
    }

    public void setDzddz(String dzddz) {
        this.dzddz = dzddz;
    }

    @Basic
    @Column(name = "mob")
    public String getArsj() {
        return arsj;
    }

    public void setArsj(String arsj) {
        this.arsj = arsj;
    }

    @Basic
    @Column(name = "homePho")
    public String getArzd() {
        return arzd;
    }

    public void setArzd(String arzd) {
        this.arzd = arzd;
    }

    @Basic
    @Column(name = "comPho")
    public String getArdwdh() {
        return ardwdh;
    }

    public void setArdwdh(String ardwdh) {
        this.ardwdh = ardwdh;
    }

    @Basic
    @Column(name = "IDNo")
    public String getArzjh() {
        return arzjh;
    }

    public void setArzjh(String arzjh) {
        this.arzjh = arzjh;
    }

    @Basic
    @Column(name = "email")
    public String getArdy() {
        return ardy;
    }

    public void setArdy(String ardy) {
        this.ardy = ardy;
    }


    @Basic
    @Column(name = "IDNoHide")
    public String getArzjhychsw() {
        return arzjhychsw;
    }

    public void setArzjhychsw(String arzjhychsw) {
        this.arzjhychsw = arzjhychsw;
    }

    @Basic
    @Column(name = "hideMidIDNo")
    public String getArzjhyczsw() {
        return arzjhyczsw;
    }

    public void setArzjhyczsw(String arzjhyczsw) {
        this.arzjhyczsw = arzjhyczsw;
    }

    @Basic
    @Column(name = "hideMidIDNoSix")
    public String getArzjhyczlw() {
        return arzjhyczlw;
    }

    public void setArzjhyczlw(String arzjhyczlw) {
        this.arzjhyczlw = arzjhyczlw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasepeoplemessageEntity)) return false;
        CasepeoplemessageEntity that = (CasepeoplemessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(wtfmc, that.wtfmc) &&
                Objects.equals(armc, that.armc) &&
                Objects.equals(arxb, that.arxb) &&
                Objects.equals(dwmc, that.dwmc) &&
                Objects.equals(dwdz, that.dwdz) &&
                Objects.equals(arbm, that.arbm) &&
                Objects.equals(arzw, that.arzw) &&
                Objects.equals(jtdz, that.jtdz) &&
                Objects.equals(hjdz, that.hjdz) &&
                Objects.equals(dzddz, that.dzddz) &&
                Objects.equals(arsj, that.arsj) &&
                Objects.equals(arzd, that.arzd) &&
                Objects.equals(ardwdh, that.ardwdh) &&
                Objects.equals(arzjh, that.arzjh) &&
                Objects.equals(ardy, that.ardy) &&
                Objects.equals(arzjhychsw, that.arzjhychsw) &&
                Objects.equals(arzjhyczsw, that.arzjhyczsw) &&
                Objects.equals(arzjhyczlw, that.arzjhyczlw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, wtfmc, armc, arxb, dwmc, dwdz, arbm, arzw, jtdz, hjdz, dzddz, arsj, arzd, ardwdh, arzjh, ardy, arzjhychsw, arzjhyczsw, arzjhyczlw);
    }
}
