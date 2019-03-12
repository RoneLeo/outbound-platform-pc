package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "casemanagement", schema = "ajjcxx", catalog = "")
public class CasemanagementEntity {
    private Integer id;
    @ApiModelProperty(value = "案件名称")
    private String ajmc;

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
    @Column(name = "casename")
    public String getAjmc() {
        return ajmc;
    }

    public void setAjmc(String ajmc) {
        this.ajmc = ajmc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CasemanagementEntity that = (CasemanagementEntity) o;
        return id == that.id &&
                Objects.equals(ajmc, that.ajmc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajmc);
    }
}
