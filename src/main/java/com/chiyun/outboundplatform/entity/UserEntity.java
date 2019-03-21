package com.chiyun.outboundplatform.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "ajjcxx", catalog = "")
public class UserEntity {
    private int id;
    @ApiModelProperty(value="用户名")
    private String yhm;
    @ApiModelProperty(value="密码")
    private String mm;
    @ApiModelProperty(value="类型")
    private String lx;
    @ApiModelProperty(value="名字")
    private String mz;
    @ApiModelProperty(value="联系电话")
    private String lxdh;
    @ApiModelProperty(value="邮箱")
    private String yx;
    @ApiModelProperty(value="性别")
    private String xb;
    @ApiModelProperty(value="年龄")
    private Integer nl;
    @ApiModelProperty(value="地址")
    private String dz;
    @ApiModelProperty(value="所在行政区代码")
    private String szxzqdm;
    @ApiModelProperty(value="状态")
    private Integer zt;
    @ApiModelProperty(value="角色id")
    private Integer js;
    @ApiModelProperty(value="openID")
    private String openid;
    @ApiModelProperty(value="sessionKey")
    private String sk;
    @ApiModelProperty(value="sessionkey创建时间")
    private Date skcjsj;
    @ApiModelProperty(value="unionID")
    private String unionid;
    @ApiModelProperty(value="绑定时间")
    private Date bdsj;
    @ApiModelProperty(value="授权码")
    private String sqm;
    @ApiModelProperty(value="总金额")
    private BigDecimal zje;
    @ApiModelProperty(value="创建时间")
    private Date cjsj;

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
    @Column(name = "user_name")
    public String getYhm() {
        return yhm;
    }

    public void setYhm(String yhm) {
        this.yhm = yhm;
    }

    @Basic
    @Column(name = "password")
    public String getMm() {
        return mm;
    }

    public void setMm(String mm) {
        this.mm = mm;
    }

    @Basic
    @Column(name = "type")
    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx;
    }

    @Basic
    @Column(name = "name")
    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    @Basic
    @Column(name = "phone")
    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    @Basic
    @Column(name = "email")
    public String getYx() {
        return yx;
    }

    public void setYx(String yx) {
        this.yx = yx;
    }

    @Basic
    @Column(name = "sex")
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @Basic
    @Column(name = "age")
    public Integer getNl() {
        return nl;
    }

    public void setNl(Integer nl) {
        this.nl = nl;
    }

    @Basic
    @Column(name = "address")
    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    @Basic
    @Column(name = "area_code")
    public String getSzxzqdm() {
        return szxzqdm;
    }

    public void setSzxzqdm(String szxzqdm) {
        this.szxzqdm = szxzqdm;
    }

    @Basic
    @Column(name = "state")
    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    @Basic
    @Column(name = "role_id")
    public Integer getJs() {
        return js;
    }

    public void setJs(Integer js) {
        this.js = js;
    }

    @Basic
    @Column(name = "wx_openID")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Basic
    @Column(name = "authorize_num")
    public String getSqm() {
        return sqm;
    }

    public void setSqm(String sqm) {
        this.sqm = sqm;
    }

    @Basic
    @Column(name = "wx_sessionKey")
    public String getSk() {
        return sk;
    }

    public void setSk(String sk) {
        this.sk = sk;
    }

    @Basic
    @Column(name = "sessionkey_time")
    public Date getSkcjsj() {
        return skcjsj;
    }

    public void setSkcjsj(Date skcjsj) {
        this.skcjsj = skcjsj;
    }

    @Basic
    @Column(name = "wx_unionID")
    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    @Basic
    @Column(name = "bind_time")
    public Date getBdsj() {
        return bdsj;
    }

    public void setBdsj(Date bdsj) {
        this.bdsj = bdsj;
    }

    @Basic
    @Column(name = "total")
    public BigDecimal getZje() {
        return zje;
    }

    public void setZje(BigDecimal zje) {
        this.zje = zje;
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
        UserEntity that = (UserEntity) o;
        return id == that.id &&
                Objects.equals(yhm, that.yhm) &&
                Objects.equals(mm, that.mm) &&
                Objects.equals(lx, that.lx) &&
                Objects.equals(mz, that.mz) &&
                Objects.equals(lxdh, that.lxdh) &&
                Objects.equals(yx, that.yx) &&
                Objects.equals(xb, that.xb) &&
                Objects.equals(nl, that.nl) &&
                Objects.equals(dz, that.dz) &&
                Objects.equals(szxzqdm, that.szxzqdm) &&
                Objects.equals(zt, that.zt) &&
                Objects.equals(js, that.js)&&
                Objects.equals(sqm, that.sqm);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, yhm, mm, lx, mz, lxdh, yx, xb, nl, dz, szxzqdm, zt, js, sqm);
    }
}
