package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "casepeoplemessage", schema = "ajjcxx", catalog = "")
public class CasepeoplemessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = "批次id")
    private String pcid;
    @ApiModelProperty(value = "案人证件号(隐藏后四位)")
    private String arzjhychsw;
    @ApiModelProperty(value = "案人证件号(隐藏中四位)")
    private String arzjhyczsw;
    @ApiModelProperty(value = "案人证件号(隐藏中六位)")
    private String arzjhyczlw;

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
    @Column(name = "batch_id")
    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid;
    }


    @Basic
    @Column(name = "IDNoHide")
    public String getArzjhychsw() {
        return arzjhychsw;
    }

    public void setArzjhychsw(String arzjhychsw) {
        this.arzjhychsw = arzjhychsw;
    }

    @Basic
    @Column(name = "hideMidIDNo")
    public String getArzjhyczsw() {
        return arzjhyczsw;
    }

    public void setArzjhyczsw(String arzjhyczsw) {
        this.arzjhyczsw = arzjhyczsw;
    }

    @Basic
    @Column(name = "hideMidIDNoSix")
    public String getArzjhyczlw() {
        return arzjhyczlw;
    }

    public void setArzjhyczlw(String arzjhyczlw) {
        this.arzjhyczlw = arzjhyczlw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CasepeoplemessageEntity)) return false;
        CasepeoplemessageEntity that = (CasepeoplemessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(pcid, that.pcid) &&
                Objects.equals(arzjhychsw, that.arzjhychsw) &&
                Objects.equals(arzjhyczsw, that.arzjhyczsw) &&
                Objects.equals(arzjhyczlw, that.arzjhyczlw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, pcid, arzjhychsw, arzjhyczsw, arzjhyczlw);
    }
}
