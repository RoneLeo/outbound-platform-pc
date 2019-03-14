package com.chiyun.outboundplatform.entity;

import javax.persistence.*;

/**
 * Created by linqi on 2019-03-14.
 * 字典项实体类
 */
@Entity
@Table(name = "dictionarylist", schema = "ajjcxx", catalog = "")
public class DictionaryListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "did")
    private Integer did;

    @Basic
    @Column(name = "zdzwm")
    private String zdzwm;

    @Basic
    @Column(name = "zdywm")
    private String zdywm;

    @Basic
    @Column(name = "zxbz")
    private String zxbz;

    @Basic
    @Column(name = "c_id")
    private String c_id;

    @Basic
    @Column(name = "c_name")
    private String c_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getZdzwm() {
        return zdzwm;
    }

    public void setZdzwm(String zdzwm) {
        this.zdzwm = zdzwm;
    }

    public String getZdywm() {
        return zdywm;
    }

    public void setZdywm(String zdywm) {
        this.zdywm = zdywm;
    }

    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DictionaryListEntity that = (DictionaryListEntity) o;

        if (!getId().equals(that.getId())) return false;
        if (!getDid().equals(that.getDid())) return false;
        if (!getZdzwm().equals(that.getZdzwm())) return false;
        if (!getZdywm().equals(that.getZdywm())) return false;
        if (!getZxbz().equals(that.getZxbz())) return false;
        if (!getC_id().equals(that.getC_id())) return false;
        return getC_name().equals(that.getC_name());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDid().hashCode();
        result = 31 * result + getZdzwm().hashCode();
        result = 31 * result + getZdywm().hashCode();
        result = 31 * result + getZxbz().hashCode();
        result = 31 * result + getC_id().hashCode();
        result = 31 * result + getC_name().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryListEntity{" +
                "id=" + id +
                ", did=" + did +
                ", zdzwm='" + zdzwm + '\'' +
                ", zdywm='" + zdywm + '\'' +
                ", zxbz='" + zxbz + '\'' +
                ", c_id='" + c_id + '\'' +
                ", c_name='" + c_name + '\'' +
                '}';
    }


}
