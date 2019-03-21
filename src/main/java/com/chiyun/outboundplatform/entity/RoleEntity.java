package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roler", schema = "ajjcxx", catalog = "")
public class RoleEntity {
    private int id;
    @ApiModelProperty(value="角色名")
    private String name;
    @ApiModelProperty(value="状态")
    private int zt;

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
    @Column(name = "role_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "state")
    public int getZt() {
        return zt;
    }

    public void setZt(int zt) {
        this.zt = zt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleEntity that = (RoleEntity) o;
        return id == that.id &&
                Objects.equals(zt, that.zt)&&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, zt);
    }
}
