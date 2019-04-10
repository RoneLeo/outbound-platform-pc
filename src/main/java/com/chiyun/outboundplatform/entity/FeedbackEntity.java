package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "feedback", schema = "ajjcxx", catalog = "")
public class FeedbackEntity {
    private Integer id;
    @ApiModelProperty(value = "任务id")
    private Integer rwid;
    @ApiModelProperty(value = "反馈人")
    private Integer fkr;
    @ApiModelProperty(value = "反馈人姓名")
    private String fkrxm;
    @ApiModelProperty(value = "反馈内容")
    private String fknr;
    @ApiModelProperty(value = "反馈时间")
    private Date fksj;
    @ApiModelProperty(value = "反馈附件")
    private String fkfj;
    @ApiModelProperty(value = "反馈状态 1-未处理 2-已处理")
    private Integer fkzt;

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
    @Column(name = "task_id")
    public Integer getRwid() {
        return rwid;
    }

    public void setRwid(Integer rwid) {
        this.rwid = rwid;
    }

    @Basic
    @Column(name = "feedback_people")
    public Integer getFkr() {
        return fkr;
    }

    public void setFkr(Integer fkr) {
        this.fkr = fkr;
    }

    @Basic
    @Column(name = "prople_name")
    public String getFkrxm() {
        return fkrxm;
    }

    public void setFkrxm(String fkrxm) {
        this.fkrxm = fkrxm;
    }

    @Basic
    @Column(name = "content")
    public String getFknr() {
        return fknr;
    }

    public void setFknr(String fknr) {
        this.fknr = fknr;
    }

    @Basic
    @Column(name = "feedback_time")
    public Date getFksj() {
        return fksj;
    }

    public void setFksj(Date fksj) {
        this.fksj = fksj;
    }

    @Basic
    @Column(name = "attachment")
    public String getFkfj() {
        return fkfj;
    }

    public void setFkfj(String fkfj) {
        this.fkfj = fkfj;
    }

    @Basic
    @Column(name = "feedback_state")
    public Integer getFkzt() {
        return fkzt;
    }

    public void setFkzt(Integer fkzt) {
        this.fkzt = fkzt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedbackEntity)) return false;
        FeedbackEntity entity = (FeedbackEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(rwid, entity.rwid) &&
                Objects.equals(fkr, entity.fkr) &&
                Objects.equals(fkrxm, entity.fkrxm) &&
                Objects.equals(fknr, entity.fknr) &&
                Objects.equals(fksj, entity.fksj) &&
                Objects.equals(fkfj, entity.fkfj) &&
                Objects.equals(fkzt, entity.fkzt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rwid, fkr, fkrxm, fknr, fksj, fkfj, fkzt);
    }
}
