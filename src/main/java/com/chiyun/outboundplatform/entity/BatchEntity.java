package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "batch", schema = "ajjcxx", catalog = "")
public class BatchEntity {
    private Integer id;
    @ApiModelProperty(value = "模板id")
    private String pcid;
    @ApiModelProperty(value = "模板名称")
    private String pcmc;
    @ApiModelProperty(value = "创建时间")
    private Date cjsj;

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
    @Column(name = "batch_id")
    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }

    @Basic
    @Column(name = "batch_name")
    public String getPcmc() {
        return pcmc;
    }

    public void setPcmc(String pcmc) {
        this.pcmc = pcmc;
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
        if (!(o instanceof BatchEntity)) return false;
        BatchEntity entity = (BatchEntity) o;
        return Objects.equals(id, entity.id) &&
                Objects.equals(pcid, entity.pcid) &&
                Objects.equals(pcmc, entity.pcmc) &&
                Objects.equals(cjsj, entity.cjsj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pcid, pcmc, cjsj);
    }
}
