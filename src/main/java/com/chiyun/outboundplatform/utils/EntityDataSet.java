package com.chiyun.outboundplatform.utils;

import com.chiyun.outboundplatform.entity.*;

/**
 * Created by wazto on 2019/3/20.
 */
public class EntityDataSet {

    /**
     * @Description:案件基本信息设置
     * @Date:2019/3/20
     */
    public static void basedataset(String data, String tagart, CasebasemessageEntity entity) {
        switch (tagart) {
            case "个案序列号":
                entity.setGaxlh(data);
                break;
            case "批次id":
                entity.setPcid(data);
                break;
            case "案件名称":
                entity.setAjmc(data);
                break;
            case "案件佣金":
                entity.setAjyj(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            default:
                break;
        }
    }

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
                entity.setQk(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "委案金额":
                entity.setWaje(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "本金":
                entity.setPcp(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "最新欠款":
                entity.setZxhk(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "利息":
                entity.setLx(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "滞纳金":
                entity.setZnj(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "已还款":
                entity.setYhk(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "最低还款":
                entity.setZdhk(StringUtil.getMoneyDouble(Double.valueOf(data)));
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
        switch (tagart) {
            case "逾期账龄":
                entity.setYqzl(data);
                break;
            case "还款期限":
                entity.setHkqx(DateUtils.getDateByString(data));
                break;
            case "信用额度":
                entity.setXyed(data);
                break;
            case "信贷分类":
                entity.setXdfl(Integer.valueOf(data));
                break;
            case "信贷分类名称":
                entity.setXdflmc(data);
                break;
            case "催收分类":
                entity.setCslx(Integer.valueOf(data));
                break;
            case "催收分类名称":
                entity.setCslxmc(data);
                break;
            case "拖欠级别":
                entity.setTqjb(Integer.valueOf(data));
                break;
            case "拖欠级别名称":
                entity.setTqjbmc(data);
                break;
            case "保证金":
                entity.setBzj(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "开卡日":
                entity.setKkr(DateUtils.getDateByString(data));
                break;
            case "最后还款日":
                entity.setZhhkr(DateUtils.getDateByString(data));
                break;
            case "最后还款额":
                entity.setZhhke(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "最后消费日":
                entity.setZhxfr(DateUtils.getDateByString(data));
                break;
            case "最后提现日":
                entity.setZhtxr(DateUtils.getDateByString(data));
                break;
            case "停卡日":
                entity.setTkr(DateUtils.getDateByString(data));
                break;
            case "账单日":
                entity.setZdr(DateUtils.getDateByString(data));
                break;
            case "人民币":
                entity.setRmb(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "外币":
                entity.setUsd(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "港币":
                entity.setGb(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "委案期数":
                entity.setWaqs(Integer.valueOf(data));
                break;
            case "逾期天数":
                entity.setYqts(Integer.valueOf(data));
                break;
            case "剩余本金":
                entity.setSybj(StringUtil.getMoneyDouble(Double.valueOf(data)));
                break;
            case "逾期期数":
                entity.setYqqs(Integer.valueOf(data));
                break;
            case "已还期数":
                entity.setYhqs(Integer.valueOf(data));
                break;
            case "商品":
                entity.setSp(data);
                break;
            case "商户":
                entity.setSh(data);
                break;
            default:
                break;
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
