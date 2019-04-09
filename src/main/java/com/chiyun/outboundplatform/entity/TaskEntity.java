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
    @ApiModelProperty(value = "审核备注")
    private String shbz;
    @ApiModelProperty(value = "实际佣金")
    private Double sjyj;
    @ApiModelProperty(value = "任务执行人id")
    private Integer rwzxr;
    @ApiModelProperty(value = "任务执行人名称")
    private String rwzxrmc;
    @ApiModelProperty(value = "任务完成时间")
    private Date rwwcsj;
    @ApiModelProperty(value = "任务创建时间")
    private Date rwcjsj;
    @ApiModelProperty(value = "数据更新时间")
    private Date gxsj;

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
    @Column(name = "check_remark")
    public String getShbz() {
        return shbz;
    }

    public void setShbz(String shbz) {
        this.shbz = shbz;
    }

    @Basic
    @Column(name = "actual_money")
    public Double getSjyj() {
        return sjyj;
    }

    public void setSjyj(Double sjyj) {
        this.sjyj = sjyj;
    }

    @Basic
    @Column(name = "task_peopleId")
    public Integer getRwzxr() {
        return rwzxr;
    }

    public void setRwzxr(Integer rwzxr) {
        this.rwzxr = rwzxr;
    }

    @Basic
    @Column(name = "task_people")
    public String getRwzxrmc() {
        return rwzxrmc;
    }

    public void setRwzxrmc(String rwzxrmc) {
        this.rwzxrmc = rwzxrmc;
    }

    @Basic
    @Column(name = "complate_time")
    public Date getRwwcsj() {
        return rwwcsj;
    }

    public void setRwwcsj(Date rwwcsj) {
        this.rwwcsj = rwwcsj;
    }

    @Basic
    @Column(name = "create_time")
    public Date getRwcjsj() {
        return rwcjsj;
    }

    public void setRwcjsj(Date rwcjsj) {
        this.rwcjsj = rwcjsj;
    }

    @Basic
    @Column(name = "update_time")
    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaskEntity)) return false;
        TaskEntity entity = (TaskEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(ajid, entity.ajid) &&
                Objects.equals(rwmc, entity.rwmc) &&
                Objects.equals(rwjzsj, entity.rwjzsj) &&
                Objects.equals(rwyj, entity.rwyj) &&
                Objects.equals(rwms, entity.rwms) &&
                Objects.equals(rwfs, entity.rwfs) &&
                Objects.equals(rwzt, entity.rwzt) &&
                Objects.equals(shbz, entity.shbz) &&
                Objects.equals(sjyj, entity.sjyj) &&
                Objects.equals(rwzxr, entity.rwzxr) &&
                Objects.equals(rwzxrmc, entity.rwzxrmc) &&
                Objects.equals(rwwcsj, entity.rwwcsj) &&
                Objects.equals(rwcjsj, entity.rwcjsj) &&
                Objects.equals(gxsj, entity.gxsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, rwmc, rwjzsj, rwyj, rwms, rwfs, rwzt, shbz, sjyj, rwzxr, rwzxrmc, rwwcsj, rwcjsj, gxsj);
    }
}
