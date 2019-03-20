package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "linkmanmessage", schema = "ajjcxx", catalog = "")
public class LinkmanmessageEntity {
    private Integer id;
    @ApiModelProperty(value = "案件id")
    private Integer ajid;
    @ApiModelProperty(value = " 联系人姓名")
    private String lxrxm;
    @ApiModelProperty(value = "联系人证件号")
    private String lxrzjh;
    @ApiModelProperty(value = "联系人关系")
    private String lxrgx;
    @ApiModelProperty(value = "联系人家庭电话")
    private String lxrjtdh;
    @ApiModelProperty(value = "联系人单位电话")
    private String lxrdwdh;
    @ApiModelProperty(value = "联系人手机")
    private String lxrsj;
    @ApiModelProperty(value = "联系人地址")
    private String lxrdz;
    @ApiModelProperty(value = "联系人单位")
    private String lxrdw;

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
    @Column(name = "ctName")
    public String getLxrxm() {
        return lxrxm;
    }

    public void setLxrxm(String lxrxm) {
        this.lxrxm = lxrxm;
    }

    @Basic
    @Column(name = "ctID")
    public String getLxrzjh() {
        return lxrzjh;
    }

    public void setLxrzjh(String lxrzjh) {
        this.lxrzjh = lxrzjh;
    }

    @Basic
    @Column(name = "ctRel")
    public String getLxrgx() {
        return lxrgx;
    }

    public void setLxrgx(String lxrgx) {
        this.lxrgx = lxrgx;
    }

    @Basic
    @Column(name = "ctHPho")
    public String getLxrjtdh() {
        return lxrjtdh;
    }

    public void setLxrjtdh(String lxrjtdh) {
        this.lxrjtdh = lxrjtdh;
    }

    @Basic
    @Column(name = "ctWPho")
    public String getLxrdwdh() {
        return lxrdwdh;
    }

    public void setLxrdwdh(String lxrdwdh) {
        this.lxrdwdh = lxrdwdh;
    }

    @Basic
    @Column(name = "ctMob")
    public String getLxrsj() {
        return lxrsj;
    }

    public void setLxrsj(String lxrsj) {
        this.lxrsj = lxrsj;
    }

    @Basic
    @Column(name = "ctAddr")
    public String getLxrdz() {
        return lxrdz;
    }

    public void setLxrdz(String lxrdz) {
        this.lxrdz = lxrdz;
    }

    @Basic
    @Column(name = "ctCom")
    public String getLxrdw() {
        return lxrdw;
    }

    public void setLxrdw(String lxrdw) {
        this.lxrdw = lxrdw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkmanmessageEntity)) return false;
        LinkmanmessageEntity that = (LinkmanmessageEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(ajid, that.ajid) &&
                Objects.equals(lxrxm, that.lxrxm) &&
                Objects.equals(lxrzjh, that.lxrzjh) &&
                Objects.equals(lxrgx, that.lxrgx) &&
                Objects.equals(lxrjtdh, that.lxrjtdh) &&
                Objects.equals(lxrdwdh, that.lxrdwdh) &&
                Objects.equals(lxrsj, that.lxrsj) &&
                Objects.equals(lxrdz, that.lxrdz) &&
                Objects.equals(lxrdw, that.lxrdw);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ajid, lxrxm, lxrzjh, lxrgx, lxrjtdh, lxrdwdh, lxrsj, lxrdz, lxrdw);
    }
}
