package com.cloud.cang.model.wz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;

import com.cloud.cang.generic.GenericEntity;

/**
 * 广告表(WZ_ADVERTIS)
 **/
public class Advertis extends GenericEntity {

    private static final long serialVersionUID = 1L;

    /*主键ID*/
    private String id;

    /*主键ID*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /* 广告类型
            10=图片
            20=视频
            30=音频 */
    private Integer iadvType;

    public Integer getIadvType() {
        return iadvType;
    }

    public void setIadvType(Integer iadvType) {
        this.iadvType = iadvType;
    }

    /* 是否可修改:是否可供用户修改 */
    private Integer idelFlag;

    public Integer getIdelFlag() {
        return idelFlag;
    }

    public void setIdelFlag(Integer idelFlag) {
        this.idelFlag = idelFlag;
    }

    /* 是否默认0:0：否
            1：是 */
    private Integer iisDefault;

    public Integer getIisDefault() {
        return iisDefault;
    }

    public void setIisDefault(Integer iisDefault) {
        this.iisDefault = iisDefault;
    }

    /* 链接类型
            1:普通超链接 
            2:内链活动
            3:内链项目
            4:内链资讯 */
    private Integer ilinkType;

    public Integer getIlinkType() {
        return ilinkType;
    }

    public void setIlinkType(Integer ilinkType) {
        this.ilinkType = ilinkType;
    }

    /* 屏幕显示类型(数据字典配置) */
    private Integer iscreenDisplayType;

    public Integer getIscreenDisplayType() {
        return iscreenDisplayType;
    }

    public void setIscreenDisplayType(Integer iscreenDisplayType) {
        this.iscreenDisplayType = iscreenDisplayType;
    }

    /* 排序号 */
    private Integer isort;

    public Integer getIsort() {
        return isort;
    }

    public void setIsort(Integer isort) {
        this.isort = isort;
    }

    /* 状态:0: INVALID:已过期
            1: USING:投放中
            2: WAIT:待投放
            3: PAUSE:暂停
             */
    private Integer istatus;

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    /* 是否通用 0否 1是*/
    private Integer iuseType;

    public Integer getIuseType() {
        return iuseType;
    }

    public void setIuseType(Integer iuseType) {
        this.iuseType = iuseType;
    }

    /* 添加人 */
    private String saddUser;

    public String getSaddUser() {
        return saddUser;
    }

    public void setSaddUser(String saddUser) {
        this.saddUser = saddUser;
    }

    /* 联系人(预留) */
    private String scontactName;

    public String getScontactName() {
        return scontactName;
    }

    public void setScontactName(String scontactName) {
        this.scontactName = scontactName;
    }

    /* 内容地址 */
    private String scontentUrl;

    public String getScontentUrl() {
        return scontentUrl;
    }

    public void setScontentUrl(String scontentUrl) {
        this.scontentUrl = scontentUrl;
    }

    /* 超链接 */
    private String shref;

    public String getShref() {
        return shref;
    }

    public void setShref(String shref) {
        this.shref = shref;
    }

    /* 商户编号 */
    private String smerchantCode;

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    /* 商户信息ID */
    private String smerchantId;

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    /* 手机(预留) */
    private String smobile;

    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    /* 广告区域code */
    private String sregionCode;

    public String getSregionCode() {
        return sregionCode;
    }

    public void setSregionCode(String sregionCode) {
        this.sregionCode = sregionCode;
    }

    /* 广告区域Id */
    private String sregionId;

    public String getSregionId() {
        return sregionId;
    }

    public void setSregionId(String sregionId) {
        this.sregionId = sregionId;
    }

    /* 说明 */
    private String sremark;

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    /* 来源标题 */
    private String ssourceTitle;

    public String getSsourceTitle() {
        return ssourceTitle;
    }

    public void setSsourceTitle(String ssourceTitle) {
        this.ssourceTitle = ssourceTitle;
    }

    /* 标题 */
    private String stitle;

    public String getStitle() {
        return stitle;
    }

    public void setStitle(String stitle) {
        this.stitle = stitle;
    }

    /* 修改人 */
    private String supdateUser;

    public String getSupdateUser() {
        return supdateUser;
    }

    public void setSupdateUser(String supdateUser) {
        this.supdateUser = supdateUser;
    }

    /* 添加日期 */
    private Date taddTime;

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }

    /* 结束日期 */
    private Date tendDate;

    public Date getTendDate() {
        return tendDate;
    }

    public void setTendDate(Date tendDate) {
        this.tendDate = tendDate;
    }

    /* 开始日期 */
    private Date tstartDate;

    public Date getTstartDate() {
        return tstartDate;
    }

    public void setTstartDate(Date tstartDate) {
        this.tstartDate = tstartDate;
    }

    /* 修改日期 */
    private Date tupdateTime;

    public Date getTupdateTime() {
        return tupdateTime;
    }

    public void setTupdateTime(Date tupdateTime) {
        this.tupdateTime = tupdateTime;
    }


}