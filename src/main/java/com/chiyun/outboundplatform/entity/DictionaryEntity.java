package com.chiyun.outboundplatform.entity;


import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;


/**
 * Created by linqi on 2019-03-14.
 * 字典名实体类
 */
@Entity
@Table(name = "dictionary", schema = "ajjcxx", catalog = "")
public class DictionaryEntity {


    private Integer id;

    @ApiModelProperty(value = "字典名称")
    private String zdmc;

    @ApiModelProperty(value = "字典代码")
    private String zddm;

    @ApiModelProperty(value = "注销标志态")
    private String zxbz;
    @ApiModelProperty(value = "字典类型")
    private  String zdlx;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @Basic
    @Column(name = "name")
    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }


    @Basic
    @Column(name = "eng_name")
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

    @Basic
    @Column(name = "type")
    public String getZdlx() {
        return zdlx;
    }

    public void setZdlx(String zdlx) {
        this.zdlx = zdlx;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DictionaryEntity)) return false;

        DictionaryEntity that = (DictionaryEntity) o;

        if (!id.equals(that.id)) return false;
        if (!zdmc.equals(that.zdmc)) return false;
        if (!zddm.equals(that.zddm)) return false;
        if(!zdlx.equals(that.zdlx)) return false;
        return zxbz.equals(that.zxbz);
    }



    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + zdmc.hashCode();
        result = 31 * result + zddm.hashCode();
        result = 31 * result + zxbz.hashCode();
        result= 31*result +zdlx.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryEntity{" +
                "id=" + id +
                ", zdmc='" + zdmc + '\'' +
                ", zddm='" + zddm + '\'' +
                ", zxbz='" + zxbz + '\'' +
                '}';
    }
}