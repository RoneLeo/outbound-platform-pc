package com.chiyun.outboundplatform.utils;

import com.chiyun.outboundplatform.entity.*;

/**
 * Created by wazto on 2019/3/20.
 */
public class EntityDataSet {
    /**
     * @Description:卡号信息设置
     * @Date:2019/3/20
     */
    public static void carddataset(String data, String tagart, CardmessageEntity card) {
        switch (tagart) {
            case "账号":
                card.setZh(data);
                break;
            case "卡号":
                card.setKh(data);
                break;
            case "卡号(隐藏后四位)":
                card.setKhychsw(data);
                break;
            case "卡号(隐藏中四位":
                card.setKhyczsw(data);
                break;
            case "卡号(隐藏中六位)":
                card.setKhyczlw(data);
                break;
            case "卡号后四位":
                card.setKhhsw(data);
                break;
            case "档案号":
                card.setDah(data);
                break;
            case "卡类":
                card.setKl(data);
                break;
            case "币种":
                card.setBz(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:催收员信息设置
     * @Date:2019/3/20
     */
    public static void empdataset(String data, String tagart, EmpmessageEntity emp) {
        switch (tagart) {
            case "催收员手机":
                emp.setCsysj(data);
                break;
            case "催收员办公电话":
                emp.setCsybgdh(data);
                break;
            case "催收员姓名":
                emp.setCsyxm(data);
                break;
            case "催收员工号":
                emp.setCsygh(data);
                break;
            case "催收部门":
                emp.setCsbm(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:贷款信息设置
     * @Date:2019/3/20
     */
    public static void loandataset(String data, String tagart, LoanmessageEntity entity) {
        switch (tagart) {
            case "欠款":
                entity.setQk(Double.valueOf(data));
                break;
            case "委案金额":
                entity.setWaje(Double.valueOf(data));
                break;
            case "本金":
                entity.setPcp(Double.valueOf(data));
                break;
            case "最新欠款":
                entity.setZxhk(Double.valueOf(data));
                break;
            case "利息":
                entity.setLx(Double.valueOf(data));
                break;
            case "滞纳金":
                entity.setZnj(Double.valueOf(data));
                break;
            case "已还款":
                entity.setYhk(Double.valueOf(data));
                break;
            case "最低还款":
                entity.setZdhk(Double.valueOf(data));
                break;
            case "委案日期":
                entity.setWarq(DateUtils.getDateByString(data));
                break;
            case "贷款日期":
                entity.setDkrq(DateUtils.getDateByString(data));
                break;
            case "预计退案日":
                entity.setYjtar(DateUtils.getDateByString(data));
                break;
            default:
                break;
        }
    }

    /**
     * @Description:对象信息设置
     * @Date:2019/3/20
     */
    public static void userdataset(String data, String tagart, UsermessageEntity entity) {
        switch (tagart) {
            case "对象姓名":
                entity.setDxxm(data);
                break;
            case "对象年龄":
                entity.setDxnl(Integer.valueOf(data));
                break;
            case "对象性别":
                entity.setDxxb(Integer.valueOf(data));
                break;
            case "地址":
                entity.setDxdz(data);
                break;
            case "地址类型":
                entity.setDzlx(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:外访信息设置
     * @Date:2019/3/20
     */
    public static void outbdataset(String data, String tagart, OutboundmessageEntity entity) {
        switch (tagart) {
            case "外访省份":
                entity.setWfsf(data);
                break;
            case "外访城市":
                entity.setWfcs(data);
                break;
            case "外访区县":
                entity.setWfqx(data);
                break;
            case "外访员":
                entity.setWfy(data);
                break;
            case "外访员ID":
                entity.setWfyid(data);
                break;
            case "外访原因":
                entity.setWfyy(data);
                break;
            case "外访要求":
                entity.setWfyq(data);
                break;
            case "外访期次":
                entity.setWfqc(Integer.valueOf(data));
                break;
            case "申请日期":
                entity.setSqrq(DateUtils.getDateByString(data));
                break;
            case "预计外访日期":
                entity.setYjwfrq(DateUtils.getDateByString(data));
                break;
            case "外访报告":
                entity.setWfbg(data);
                break;
            case "外访备注":
                entity.setWfbz(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:案人信息设置
     * @Date:2019/3/20
     */
    public static void casepdataset(String data, String tagart, CasepeoplemessageEntity entity) {
        switch (tagart) {
            case "委托方名称":
                entity.setWtfmc(data);
                break;
            case "案人名称":
                entity.setArmc(data);
                break;
            case "案人性别":
                entity.setArxb(Integer.valueOf(data));
                break;
            case "单位名称":
                entity.setDwmc(data);
                break;
            case "单位地址":
                entity.setDwdz(data);
                break;
            case "案人部门":
                entity.setArbm(data);
                break;
            case "案人职位":
                entity.setArzw(data);
                break;
            case "家庭地址":
                entity.setJtdz(data);
                break;
            case "户籍地址":
                entity.setHjdz(data);
                break;
            case "对账单地址":
                entity.setDzddz(data);
                break;
            case "案人手机":
                entity.setArsj(data);
                break;
            case "案人宅电":
                entity.setArzd(data);
                break;
            case "案人单位电话":
                entity.setArdwdh(data);
                break;
            case "案人证件号":
                entity.setArzjh(data);
                break;
            case "案人电邮":
                entity.setArdy(data);
                break;
            case "案人证件号(隐藏后四位)":
                entity.setArzjhychsw(data);
                break;
            case "案人证件号(隐藏中四位)":
                entity.setArzjhyczsw(data);
                break;
            case "案人证件号(隐藏中六位)":
                entity.setArzjhyczlw(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:联系人信息设置
     * @Date:2019/3/20
     */
    public static void linkdataset(String data, String tagart, LinkmanmessageEntity entity) {
        switch (tagart) {
            case "联系人姓名":
                entity.setLxrxm(data);
                break;
            case "联系人证件号":
                entity.setLxrzjh(data);
                break;
            case "联系人关系":
                entity.setLxrgx(data);
                break;
            case "联系人家庭电话":
                entity.setLxrjtdh(data);
                break;
            case "联系人单位电话":
                entity.setLxrdwdh(data);
                break;
            case "联系人手机":
                entity.setLxrsj(data);
                break;
            case "联系人地址":
                entity.setLxrdz(data);
                break;
            case "联系人单位":
                entity.setLxrdw(data);
                break;
            default:
                break;
        }
    }

    /**
     * @Description:其他信息设置
     * @Date:2019/3/20
     */
    public static void otherdataset(String data, String tagart, OthermessageEntity entity) {
        if (tagart.equals("商户")) {
            entity.setSh(data);
        }
    }

    /**
     * @Description:备注信息设置
     * @Date:2019/3/20
     */
    public static void remarkdataset(String data, String tagart, RemarkmsgEntity entity) {
        if (tagart.equals("备注")) {
            entity.setRemark(data);
        }
    }
}
