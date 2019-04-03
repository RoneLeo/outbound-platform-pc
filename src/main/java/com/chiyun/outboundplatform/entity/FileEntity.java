package com.chiyun.outboundplatform.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file", schema = "ajjcxx", catalog = "")
public class FileEntity {
    private Integer id;
    private String wjmc;
    private String wjdz;

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
    @Column(name = "file_name")
    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    @Basic
    @Column(name = "file_address")
    public String getWjdz() {
        return wjdz;
    }

    public void setWjdz(String wjdz) {
        this.wjdz = wjdz;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity that = (FileEntity) o;
        return id == that.id &&
                Objects.equals(wjmc, that.wjmc) &&
                Objects.equals(wjdz, that.wjdz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wjmc, wjdz);
    }
}
