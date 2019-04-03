package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "log", schema = "ajjcxx", catalog = "")
public class LogEntity {
    private int id;
    @ApiModelProperty(value = "操作人")
    private String czr;
    @ApiModelProperty(value = "操作事件")
    private String czsj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "user_name")
    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr;
    }

    @Basic
    @Column(name = "event")
    public String getCzsj() {
        return czsj;
    }

    public void setCzsj(String czsj) {
        this.czsj = czsj;
    }

    @Basic
    @Column(name = "remark")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Basic
    @Column(name = "create_time")
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEntity logEntity = (LogEntity) o;
        return id == logEntity.id &&
                Objects.equals(czr, logEntity.czr) &&
                Objects.equals(czsj, logEntity.czsj) &&
                Objects.equals(bz, logEntity.bz) &&
                Objects.equals(cjsj, logEntity.cjsj);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, czr, czsj, bz, cjsj);
    }
}
