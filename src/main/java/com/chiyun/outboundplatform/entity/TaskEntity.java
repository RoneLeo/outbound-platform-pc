package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "task", schema = "ajjcxx", catalog = "")
public class TaskEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "任务名称")
    private String rwmc;
    @ApiModelProperty(value = "任务截止时间")
    private Date rwjzsj;
    @ApiModelProperty(value = "任务佣金")
    private Double rwyj;
    @ApiModelProperty(value = "任务描述")
    private String rwms;
    @ApiModelProperty(value = "任务方式")
    private Integer rwfs;
    @ApiModelProperty(value = "任务状态")
    private Integer rwzt;
    @ApiModelProperty(value = "任务执行人")
    private Integer rwzxr;
    @ApiModelProperty(value = "任务完成时间")
    private Date rwwcsj;

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
    @Column(name = "case_id")
    public Integer getAjid() {
        return ajid;
    }

    public void setAjid(Integer ajid) {
        this.ajid = ajid;
    }

    @Basic
    @Column(name = "task_name")
    public String getRwmc() {
        return rwmc;
    }

    public void setRwmc(String rwmc) {
        this.rwmc = rwmc;
    }

    @Basic
    @Column(name = "task_time")
    public Date getRwjzsj() {
        return rwjzsj;
    }

    public void setRwjzsj(Date rwjzsj) {
        this.rwjzsj = rwjzsj;
    }

    @Basic
    @Column(name = "task_money")
    public Double getRwyj() {
        return rwyj;
    }

    public void setRwyj(Double rwyj) {
        this.rwyj = rwyj;
    }

    @Basic
    @Column(name = "task_description")
    public String getRwms() {
        return rwms;
    }

    public void setRwms(String rwms) {
        this.rwms = rwms;
    }

    @Basic
    @Column(name = "task_way")
    public Integer getRwfs() {
        return rwfs;
    }

    public void setRwfs(Integer rwfs) {
        this.rwfs = rwfs;
    }

    @Basic
    @Column(name = "task_state")
    public Integer getRwzt() {
        return rwzt;
    }

    public void setRwzt(Integer rwzt) {
        this.rwzt = rwzt;
    }

    @Basic
    @Column(name = "task_people")
    public Integer getRwzxr() {
        return rwzxr;
    }

    public void setRwzxr(Integer rwzxr) {
        this.rwzxr = rwzxr;
    }

    @Basic
    @Column(name = "complate_time")
    public Date getRwwcsj() {
        return rwwcsj;
    }

    public void setRwwcsj(Date rwwcsj) {
        this.rwwcsj = rwwcsj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return id == that.id &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(rwmc, that.rwmc) &&
                Objects.equals(rwjzsj, that.rwjzsj) &&
                Objects.equals(rwyj, that.rwyj) &&
                Objects.equals(rwms, that.rwms) &&
                Objects.equals(rwfs, that.rwfs) &&
                Objects.equals(rwzt, that.rwzt) &&
                Objects.equals(rwzxr, that.rwzxr) &&
                Objects.equals(rwwcsj, that.rwwcsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, rwmc, rwjzsj, rwyj, rwms, rwfs, rwzt, rwzxr, rwwcsj);
    }
}
