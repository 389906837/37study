package com.cloud.cang.bzc.om.vo;

import java.util.Date;
import java.util.List;

/**
 * Created by Alex on 2018/1/9.
 */
public class ActivityVo {
    private String id;  /* 活动配置ID */

    private String sconfCode;   /* 活动配置编号 */

    private String sconfName;   /* 活动配置名称 */

    private Integer iisAvailable;   /* 活动是否启用 */

    private Integer itype;  /* 活动分类   10:场景活动 20:促销活动 */

    private Integer idiscountWay;   /* 优惠方式 10：首单 20：打折 30：满减 40：返券 50：返现 */

    private Integer irangeType;  /* 应用范围类型 10:全部   20:部分设备 30:部分商品 40:部分设备的部分商品 */

    private Integer iallCount;   /* 活动期间总参与次数(0不限制) */

    private Integer icountType;  /* 次数限制类型 10：活动期间 20：活动期间设备   30：活动期间每天 */

    private Integer iuserAllCount;   /* 活动期间用户总参与次数（0不限制） */

    private Integer iuserDayCount;   /* 单日用户总参与次数上限（0不限制） */

    private Integer iisSuperposition;    /* 优惠是否叠加(首单) */

    private Date tactiveStartTime;  /* 活动开始时间 */

    private Date tactiveEndTime;    /* 活动结束时间 */

    private Integer ijoinNum;       /* 已参与人数（活动优惠记录统计） */

    private ActivityDiscountDetailVo acDisDetail;  /* 活动优惠信息明细 */

    private ActivityUseRangeVo acUseRange;  /* 活动应用范围明细 */

    private List<ActivityDiscountDetailVo> discountDetailList;     /* 活动优惠信息明细集合 */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSconfCode() {
        return sconfCode;
    }

    public void setSconfCode(String sconfCode) {
        this.sconfCode = sconfCode;
    }

    public String getSconfName() {
        return sconfName;
    }

    public void setSconfName(String sconfName) {
        this.sconfName = sconfName;
    }

    public Integer getIisAvailable() {
        return iisAvailable;
    }

    public void setIisAvailable(Integer iisAvailable) {
        this.iisAvailable = iisAvailable;
    }

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public Integer getIdiscountWay() {
        return idiscountWay;
    }

    public void setIdiscountWay(Integer idiscountWay) {
        this.idiscountWay = idiscountWay;
    }

    public Integer getIrangeType() {
        return irangeType;
    }

    public void setIrangeType(Integer irangeType) {
        this.irangeType = irangeType;
    }

    public Integer getIallCount() {
        return iallCount;
    }

    public void setIallCount(Integer iallCount) {
        this.iallCount = iallCount;
    }

    public Integer getIcountType() {
        return icountType;
    }

    public void setIcountType(Integer icountType) {
        this.icountType = icountType;
    }

    public Integer getIuserAllCount() {
        return iuserAllCount;
    }

    public void setIuserAllCount(Integer iuserAllCount) {
        this.iuserAllCount = iuserAllCount;
    }

    public Integer getIuserDayCount() {
        return iuserDayCount;
    }

    public void setIuserDayCount(Integer iuserDayCount) {
        this.iuserDayCount = iuserDayCount;
    }

    public Integer getIisSuperposition() {
        return iisSuperposition;
    }

    public void setIisSuperposition(Integer iisSuperposition) {
        this.iisSuperposition = iisSuperposition;
    }

    public Date getTactiveStartTime() {
        return tactiveStartTime;
    }

    public void setTactiveStartTime(Date tactiveStartTime) {
        this.tactiveStartTime = tactiveStartTime;
    }

    public Date getTactiveEndTime() {
        return tactiveEndTime;
    }

    public void setTactiveEndTime(Date tactiveEndTime) {
        this.tactiveEndTime = tactiveEndTime;
    }

    public Integer getIjoinNum() {
        return ijoinNum;
    }

    public void setIjoinNum(Integer ijoinNum) {
        this.ijoinNum = ijoinNum;
    }

    public ActivityDiscountDetailVo getAcDisDetail() {
        return acDisDetail;
    }

    public void setAcDisDetail(ActivityDiscountDetailVo acDisDetail) {
        this.acDisDetail = acDisDetail;
    }

    public ActivityUseRangeVo getAcUseRange() {
        return acUseRange;
    }

    public void setAcUseRange(ActivityUseRangeVo acUseRange) {
        this.acUseRange = acUseRange;
    }

    public List<ActivityDiscountDetailVo> getDiscountDetailList() {
        return discountDetailList;
    }

    public void setDiscountDetailList(List<ActivityDiscountDetailVo> discountDetailList) {
        this.discountDetailList = discountDetailList;
    }
}
