package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cardmessage", schema = "ajjcxx", catalog = "")
public class CardmessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "卡号(隐藏后四位)")
    private String khychsw;
    @ApiModelProperty(value = "卡号(隐藏中四位)")
    private String khyczsw;
    @ApiModelProperty(value = "卡号(隐藏中六位)")
    private String khyczlw;
    @ApiModelProperty(value = "卡号后四位")
    private String khhsw;
    @ApiModelProperty(value = " 账号")
    private String zh;
    @ApiModelProperty(value = "档案号")
    private String dah;
    @ApiModelProperty(value = "卡类")
    private String kl;


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
    @Column(name = "cardNoHide")
    public String getKhychsw() {
        return khychsw;
    }

    public void setKhychsw(String khychsw) {
        this.khychsw = khychsw;
    }

    @Basic
    @Column(name = "hideMidCNo")
    public String getKhyczsw() {
        return khyczsw;
    }

    public void setKhyczsw(String khyczsw) {
        this.khyczsw = khyczsw;
    }

    @Basic
    @Column(name = "hideMidCNoSix")
    public String getKhyczlw() {
        return khyczlw;
    }

    public void setKhyczlw(String khyczlw) {
        this.khyczlw = khyczlw;
    }

    @Basic
    @Column(name = "cardTailNo")
    public String getKhhsw() {
        return khhsw;
    }

    public void setKhhsw(String khhsw) {
        this.khhsw = khhsw;
    }

    @Basic
    @Column(name = "account")
    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    @Basic
    @Column(name = "fileNo")
    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    @Basic
    @Column(name = "cardCat")
    public String getKl() {
        return kl;
    }

    public void setKl(String kl) {
        this.kl = kl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardmessageEntity)) return false;
        CardmessageEntity that = (CardmessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(khychsw, that.khychsw) &&
                Objects.equals(khyczsw, that.khyczsw) &&
                Objects.equals(khyczlw, that.khyczlw) &&
                Objects.equals(khhsw, that.khhsw) &&
                Objects.equals(zh, that.zh) &&
                Objects.equals(dah, that.dah) &&
                Objects.equals(kl, that.kl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, khychsw, khyczsw, khyczlw, khhsw, zh, dah, kl);
    }
}
