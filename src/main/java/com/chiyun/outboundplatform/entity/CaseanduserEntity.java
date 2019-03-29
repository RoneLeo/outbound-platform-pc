package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by wazto on 2019/3/25.
 */
@Entity
@ApiModel
@Table(name = "caseanduser", schema = "ajjcxx", catalog = "")
public class CaseanduserEntity {
    @ApiModelProperty("案件id")
    private int ajid;
    @ApiModelProperty("用户id")
    private Integer yhid;

    @Id
    @Column(name = "caseid")
    public int getAjid() {
        return ajid;
    }

    public void setAjid(int ajid) {
        this.ajid = ajid;
    }

    @Basic
    @Column(name = "userid")
    public Integer getYhid() {
        return yhid;
    }

    public void setYhid(Integer yhid) {
        this.yhid = yhid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaseanduserEntity that = (CaseanduserEntity) o;
        return ajid == that.ajid &&
                Objects.equals(yhid, that.yhid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ajid, yhid);
    }
}
