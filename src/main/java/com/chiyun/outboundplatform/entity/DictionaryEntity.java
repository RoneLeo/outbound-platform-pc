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

    @ApiModelProperty(value = "字典中文名称")
    private String zdzwmc;

    @ApiModelProperty(value = "字典英文名称")
    private String zdywmc;

    @ApiModelProperty(value = "状注销标志态")
    private String zxbz;


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
    public String getZdzwmc() {
        return zdzwmc;
    }

    public void setZdzwmc(String zdzwmc) {
        this.zdzwmc= zdzwmc;
    }



    @Basic
    @Column(name = "eng_name")
    public String getZdywmc() {
        return zdywmc;
    }

    public void setZdywmc(String zdywmc) {
        this.zdywmc= zdywmc;
    }



    @Basic
    @Column(name = "state")
    public String getZxbz() {
        return zxbz;
    }

    public void setZxbz(String zxbz) {
        this.zxbz = zxbz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DictionaryEntity)) return false;

        DictionaryEntity that = (DictionaryEntity) o;

        if (!id.equals(that.id)) return false;
        if (!zdzwmc.equals(that.zdzwmc)) return false;
        if (!zdywmc.equals(that.zdywmc)) return false;
        return zxbz.equals(that.zxbz);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + zdzwmc.hashCode();
        result = 31 * result + zdywmc.hashCode();
        result = 31 * result + zxbz.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryEntity{" +
                "id=" + id +
                ", zdzwmc='" + zdzwmc + '\'' +
                ", zdywmc='" + zdywmc + '\'' +
                ", zxbz='" + zxbz + '\'' +
                '}';
    }
}