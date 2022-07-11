package com.cloud.cang.model.op;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;

/**
 * 接口账户记录表(OP_INTERFACE_ACCOUNT)
 **/
public class InterfaceAccount extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*主键*/
    private String id;

    /*主键*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 添加日期 */
    private Date addTime;

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /* 余额次数 */
    private Integer fbalance;

    public Integer getFbalance() {
        return fbalance;
    }

    public void setFbalance(Integer fbalance) {
        this.fbalance = fbalance;
    }

    /* 账户类型
            10=按次计费
            20=按单位次数计费 （识别图片张数）
            30=按时间计费
            40=通用时间按次收费
            50=通用时间按单位次数计费
             */
    private Integer iaccountType;

    public Integer getIaccountType() {
        return iaccountType;
    }

    public void setIaccountType(Integer iaccountType) {
        this.iaccountType = iaccountType;
    }

    /* 扣费调用次数 */
    private Integer ideductionNum;

    public Integer getIdeductionNum() {
        return ideductionNum;
    }

    public void setIdeductionNum(Integer ideductionNum) {
        this.ideductionNum = ideductionNum;
    }

    /* 是否删除0否1是 */
    private Integer idelFlag;

    public Integer getIdelFlag() {
        return idelFlag;
    }

    public void setIdelFlag(Integer idelFlag) {
        this.idelFlag = idelFlag;
    }

    /* 冻结次数 */
    private Integer ifreezeNum;

    public Integer getIfreezeNum() {
        return ifreezeNum;
    }

    public void setIfreezeNum(Integer ifreezeNum) {
        this.ifreezeNum = ifreezeNum;
    }

    /* 是否不限制次数
0:不限次数;
1:限制次数 */
    private Integer iisUnlimitedNumber;

    public Integer getIisUnlimitedNumber() {
        return iisUnlimitedNumber;
    }

    public void setIisUnlimitedNumber(Integer iisUnlimitedNumber) {
        this.iisUnlimitedNumber = iisUnlimitedNumber;
    }

    /* 状态
            10=已开通
            20=已关闭 */
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 调用次数 */
    private Integer itransferNum;

    public Integer getItransferNum() {
        return itransferNum;
    }

    public void setItransferNum(Integer itransferNum) {
        this.itransferNum = itransferNum;
    }

    /* 有效期类型
            10=固定日期
            20=长期有效 */
    private Integer ivalidityType;

    public Integer getIvalidityType() {
        return ivalidityType;
    }

    public void setIvalidityType(Integer ivalidityType) {
        this.ivalidityType = ivalidityType;
    }

    /* 账户编号 */
    private String scode;

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    /* 接口编号 */
    private String sinterfaceCode;

    public String getSinterfaceCode() {
        return sinterfaceCode;
    }

    public void setSinterfaceCode(String sinterfaceCode) {
        this.sinterfaceCode = sinterfaceCode;
    }

    /* 接口ID */
    private String sinterfaceId;

    public String getSinterfaceId() {
        return sinterfaceId;
    }

    public void setSinterfaceId(String sinterfaceId) {
        this.sinterfaceId = sinterfaceId;
    }

    /* 备注 */
    private String sremark;

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    /* 用户编号 */
    private String suserCode;

    public String getSuserCode() {
        return suserCode;
    }

    public void setSuserCode(String suserCode) {
        this.suserCode = suserCode;
    }

    /* 用户ID */
    private String suserId;

    public String getSuserId() {
        return suserId;
    }

    public void setSuserId(String suserId) {
        this.suserId = suserId;
    }

    /* 结束时间 */
    private Date tendTime;

    public Date getTendTime() {
        return tendTime;
    }

    public void setTendTime(Date tendTime) {
        this.tendTime = tendTime;
    }

    /* 开通时间 */
    private Date topenTime;

    public Date getTopenTime() {
        return topenTime;
    }

    public void setTopenTime(Date topenTime) {
        this.topenTime = topenTime;
    }

    /* 开始时间 */
    private Date tstartTime;

    public Date getTstartTime() {
        return tstartTime;
    }

    public void setTstartTime(Date tstartTime) {
        this.tstartTime = tstartTime;
    }

    /* 修改日期 */
    private Date updateTime;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


}