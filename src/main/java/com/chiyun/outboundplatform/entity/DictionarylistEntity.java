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
    @ApiModelProperty(value = "字典中文名称")
    private String zdzwmc;
    @ApiModelProperty(value = "字典英文名称")
    private String zdywmc;
    @ApiModelProperty(value = "注销标志【0：未注销，1：已注销】")
    private String zxbz;
    @ApiModelProperty(value = "字典项词条代码")
    private String ctdm;
    @ApiModelProperty(value = "字典项词条名称")
    private String ctmc;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    public String getZdzwmc() {
        return zdzwmc;
    }

    public void setZdzwmc(String zdzwmc) {
        this.zdzwmc = zdzwmc;
    }

    @Basic
    @Column(name = "dicteng_name")
    public String getZdywmc() {
        return zdywmc;
    }

    public void setZdywmc(String zdywmc) {
        this.zdywmc = zdywmc;
    }

    @Basic
    @Column(name = "state")
    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    @Basic
    @Column(name = "entrycode")
    public String getCtdm() {
        return ctdm;
    }

    public void setCtdm(String ctdm) {
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
        if (o == null || getClass() != o.getClass()) return false;

        DictionarylistEntity that = (DictionarylistEntity) o;

        if (id != that.id) return false;
        if (zdid != that.zdid) return false;
        if (zdzwmc != null ? !zdzwmc.equals(that.zdzwmc) : that.zdzwmc != null) return false;
        if (zdywmc != null ? !zdywmc.equals(that.zdywmc) : that.zdywmc != null) return false;
        if (zxbz != null ? !zxbz.equals(that.zxbz) : that.zxbz != null) return false;
        if (ctdm != null ? !ctdm.equals(that.ctdm) : that.ctdm != null) return false;
        if (ctmc != null ? !ctmc.equals(that.ctmc) : that.ctmc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + zdid;
        result = 31 * result + (zdzwmc != null ? zdzwmc.hashCode() : 0);
        result = 31 * result + (zdywmc != null ? zdywmc.hashCode() : 0);
        result = 31 * result + (zxbz != null ? zxbz.hashCode() : 0);
        result = 31 * result + (ctdm != null ? ctdm.hashCode() : 0);
        result = 31 * result + (ctmc != null ? ctmc.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DictionarylistEntity{" +
                "id=" + id +
                ", zdid=" + zdid +
                ", zdzwmc='" + zdzwmc + '\'' +
                ", zdywmc='" + zdywmc + '\'' +
                ", zxbz='" + zxbz + '\'' +
                ", ctdm='" + ctdm + '\'' +
                ", ctz='" + ctmc + '\'' +
                '}';
    }
}
