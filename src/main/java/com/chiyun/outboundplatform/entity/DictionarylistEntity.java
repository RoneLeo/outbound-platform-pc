package com.chiyun.outboundplatform.entity;

import javax.persistence.*;
import io.swagger.annotations.ApiModelProperty;
/**
 * Created by Linqi on 2019-03-19.
 */
@Entity
@Table(name = "dictionarylist", schema = "ajjcxx", catalog = "")
public class DictionarylistEntity {
    private int id;
    @ApiModelProperty(value = "字典ID")
    private int zdid;
    @ApiModelProperty(value = "字典名称")
    private String zdmc;
    @ApiModelProperty(value = "字典代码")
    private String zddm;
    @ApiModelProperty(value = "注销标志【0：未注销，1：已注销】")
    private String zxbz;
    @ApiModelProperty(value = "词条代码")
    private Integer ctdm;
    @ApiModelProperty(value = "词条名称")
    private String ctmc;

    @Basic
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "dictid")
    public int getZdid() {
        return zdid;
    }

    public void setZdid(int zdid) {
        this.zdid = zdid;
    }

    @Basic
    @Column(name = "dictname")
    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    @Basic
    @Column(name = "dicteng_name")
    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    @Basic
    @Column(name = "state")
    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    @Id
    @Column(name = "entrycode")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getCtdm() {
        return ctdm;
    }

    public void setCtdm(Integer ctdm) {
        this.ctdm = ctdm;
    }

    @Basic
    @Column(name = "entryvalue")
    public String getCtmc() {
        return ctmc;
    }

    public void setCtmc(String ctmc) {
        this.ctmc = ctmc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DictionarylistEntity)) return false;

        DictionarylistEntity that = (DictionarylistEntity) o;

        if (id != that.id) return false;
        if (zdid != that.zdid) return false;
        if (!zdmc.equals(that.zdmc)) return false;
        if (!zddm.equals(that.zddm)) return false;
        if (!zxbz.equals(that.zxbz)) return false;
        if (!ctdm.equals(that.ctdm)) return false;
        return ctmc.equals(that.ctmc);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + zdid;
        result = 31 * result + zdmc.hashCode();
        result = 31 * result + zddm.hashCode();
        result = 31 * result + zxbz.hashCode();
        result = 31 * result + ctdm.hashCode();
        result = 31 * result + ctmc.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DictionarylistEntity{" +
                "id=" + id +
                ", zdid=" + zdid +
                ", zdmc='" + zdmc + '\'' +
                ", zddm='" + zddm + '\'' +
                ", zxbz='" + zxbz + '\'' +
                ", ctdm='" + ctdm + '\'' +
                ", ctz='" + ctmc + '\'' +
                '}';
    }
}
