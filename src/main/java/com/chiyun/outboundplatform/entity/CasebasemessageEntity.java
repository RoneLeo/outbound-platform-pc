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
    @ApiModelProperty(value = "批次号")
    private String pch;
    @ApiModelProperty(value = "卡号")
    private String kh;
    @ApiModelProperty(value = "币种")
    private String bz;
    @ApiModelProperty(value = "委案日期")
    private Date warq;
    @ApiModelProperty(value = "贷款日期")
    private Date dkrq;
    @ApiModelProperty(value = "委案金额")
    private Double waje;
    @ApiModelProperty(value = "委托方名称")
    private String wtfmc;
    @ApiModelProperty(value = "案人名称")
    private String armc;
    @ApiModelProperty(value = "案人性别")
    private Integer arxb;
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
    @ApiModelProperty(value = "当前日期")
    private Date dqrq;
    @ApiModelProperty(value = "联系人信息")
    private List<LinkmanmessageEntity> lxrxx;
    @ApiModelProperty(value = "贷款信息")
    private LoanmessageEntity dkxx;
    @ApiModelProperty(value = "卡号信息")
    private CardmessageEntity khxx;
    @ApiModelProperty(value = "催收员信息")
    private EmpmessageEntity csxx;
    @ApiModelProperty(value = "外访信息")
    private List<OutboundmessageEntity> wfxx;
    @ApiModelProperty(value = "其他信息")
    private OthermessageEntity qtxx;
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
    @Column(name = "caseNo")
    public String getGaxlh() {
        return gaxlh;
    }

    public void setGaxlh(String gaxlh) {
        this.gaxlh = gaxlh;
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
    @Column(name = "cardNo")
    public String getKh() {
        return kh;
    }

    public void setKh(String kh) {
        this.kh = kh;
    }

    @Basic
    @Column(name = "mCat")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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
    public Integer getArxb() {
        return arxb;
    }

    public void setArxb(Integer arxb) {
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
    @Column(name = "curDate")
    public Date getDqrq() {
        return dqrq;
    }

    public void setDqrq(Date dqrq) {
        this.dqrq = dqrq;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="casebase_id")
    public List<LinkmanmessageEntity> getLxrxx() {
        return lxrxx;
    }

    public void setLxrxx(List<LinkmanmessageEntity> lxrxx) {
        this.lxrxx = lxrxx;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "loanMsg", unique = true)
    public LoanmessageEntity getDkxx() {
        return dkxx;
    }

    public void setDkxx(LoanmessageEntity dkxx) {
        this.dkxx = dkxx;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardMsg", unique = true)
    public CardmessageEntity getKhxx() {
        return khxx;
    }

    public void setKhxx(CardmessageEntity khxx) {
        this.khxx = khxx;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "empMsg", unique = true)
    public EmpmessageEntity getCsxx() {
        return csxx;
    }

    public void setCsxx(EmpmessageEntity csxx) {
        this.csxx = csxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="casebase_id")
    public List<OutboundmessageEntity> getWfxx() {
        return wfxx;
    }

    public void setWfxx(List<OutboundmessageEntity> wfxx) {
        this.wfxx = wfxx;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "otherMsg", unique = true)
    public OthermessageEntity getQtxx() {
        return qtxx;
    }

    public void setQtxx(OthermessageEntity qtxx) {
        this.qtxx = qtxx;
    }

    @OneToMany(cascade={CascadeType.ALL})
    @JoinColumn(name="casebase_id")
    public List<RemarkmsgEntity> getRemarkMsg() {
        return bzxx;
    }

    public void setRemarkMsg(List<RemarkmsgEntity> bzxx) {
        this.bzxx = bzxx;
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
                Objects.equals(pch, that.pch) &&
                Objects.equals(kh, that.kh) &&
                Objects.equals(bz, that.bz) &&
                Objects.equals(warq, that.warq) &&
                Objects.equals(dkrq, that.dkrq) &&
                Objects.equals(waje, that.waje) &&
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
                Objects.equals(dqrq, that.dqrq) &&
                Objects.equals(lxrxx, that.lxrxx) &&
                Objects.equals(dkxx, that.dkxx) &&
                Objects.equals(khxx, that.khxx) &&
                Objects.equals(csxx, that.csxx) &&
                Objects.equals(wfxx, that.wfxx) &&
                Objects.equals(qtxx, that.qtxx) &&
                Objects.equals(bzxx, that.bzxx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, gaxlh, pch, kh, bz, warq, dkrq, waje, wtfmc, armc, arxb, dwmc, dwdz, arbm, arzw, jtdz, hjdz, dzddz, arsj, arzd, ardwdh, arzjh, ardy, dqrq, lxrxx, dkxx, khxx, csxx, wfxx, qtxx, bzxx);
    }
}
