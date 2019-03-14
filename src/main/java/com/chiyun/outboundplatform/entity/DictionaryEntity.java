package com.chiyun.outboundplatform.entity;


import javax.persistence.*;


/**
 * Created by linqi on 2019-03-14.
 * 字典名实体类
 */
@Entity
@Table(name = "dictionary", schema = "ajjcxx", catalog = "")
public class DictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "eng_name")
    private String eng_name;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column(name = "zxbz")
    private String zxbz;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEng_name() {
        return eng_name;
    }

    public void setEng_name(String eng_name) {
        this.eng_name = eng_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
        if (!name.equals(that.name)) return false;
        if (!eng_name.equals(that.eng_name)) return false;
        if (!type.equals(that.type)) return false;
        return zxbz.equals(that.zxbz);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + eng_name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + zxbz.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "DictionaryEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", eng_name='" + eng_name + '\'' +
                ", type='" + type + '\'' +
                ", zxbz='" + zxbz + '\'' +
                '}';
    }
}