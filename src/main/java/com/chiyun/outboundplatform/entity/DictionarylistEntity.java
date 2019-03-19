package com.chiyun.outboundplatform.entity;

import javax.persistence.*;

/**
 * Created by Linqi on 2019-03-19.
 */
@Entity
@Table(name = "dictionarylist", schema = "ajjcxx", catalog = "")
public class DictionarylistEntity {
    private int id;
    private int zdid;
    private String zdzwmc;
    private String dictengName;
    private String state;
    private String ctdm;
    private String ctz;

    @Id
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
    public String getZdzwmc() {
        return zdzwmc;
    }

    public void setZdzwmc(String zdzwmc) {
        this.zdzwmc = zdzwmc;
    }

    @Basic
    @Column(name = "dicteng_name")
    public String getDictengName() {
        return dictengName;
    }

    public void setDictengName(String dictengName) {
        this.dictengName = dictengName;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    public String getCtz() {
        return ctz;
    }

    public void setCtz(String ctz) {
        this.ctz = ctz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionarylistEntity that = (DictionarylistEntity) o;

        if (id != that.id) return false;
        if (zdid != that.zdid) return false;
        if (zdzwmc != null ? !zdzwmc.equals(that.zdzwmc) : that.zdzwmc != null) return false;
        if (dictengName != null ? !dictengName.equals(that.dictengName) : that.dictengName != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (ctdm != null ? !ctdm.equals(that.ctdm) : that.ctdm != null) return false;
        if (ctz != null ? !ctz.equals(that.ctz) : that.ctz != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + zdid;
        result = 31 * result + (zdzwmc != null ? zdzwmc.hashCode() : 0);
        result = 31 * result + (dictengName != null ? dictengName.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (ctdm != null ? ctdm.hashCode() : 0);
        result = 31 * result + (ctz != null ? ctz.hashCode() : 0);
        return result;
    }
}
