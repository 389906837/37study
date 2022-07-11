package com.antbox.rfidmachine.domain;

import com.antbox.rfidmachine.enumclass.QualityGuaranteePeriodType;

import java.util.Date;

/**
 * Created by chenzw on 2017/9/9
 * <p>
 * 商品批次号
 */

public class ProductLotno {
    private static final long serialVersionUID = 1L;

//    @Column(columnDefinition = "varchar(30) comment '批次号'", nullable = false)
    private String lotno;
//    @Column(columnDefinition = "bigint comment '产品ID'", nullable = false)
    private Long productId;
//    @Column(columnDefinition = "date comment '生产日期'")
    private Date productionDate;
//    @Column(columnDefinition = "date comment '过期日期'")
    private Date expiredDate;
//    @Column(columnDefinition = "bit default 0 comment '是否停用(是:1,否:0)'")
    private Boolean disabled;
//    @Column(columnDefinition = "varchar(50) comment '提示'")
    private String tips;
//    @Column(columnDefinition = "BIGINT COMMENT '加盟商ID'")
    private Long merchantId;
//    @Column(columnDefinition = "BIGINT COMMENT '平台ID'")
    private Long mallId;
    private String qualityGuaranteePeriod;
//    @Column(columnDefinition = " varchar(20) comment '保质期类型 DAY(0,\"日\"), MONTH(1,\"月\")'")
//    @Enumerated(EnumType.STRING)
    private QualityGuaranteePeriodType qualityGuaranteePeriodType;
//    @Column(columnDefinition = "date COMMENT '过期提醒日期'")
    private Date remindBefore;

    public Date getRemindBefore() {
        return remindBefore;
    }

    public void setRemindBefore(Date remindBefore) {
        this.remindBefore = remindBefore;
    }

    public String getLotno() {
        return lotno;
    }

    public void setLotno(String lotno) {
        this.lotno = lotno;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    public String getTips() {
        return tips;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public String getQualityGuaranteePeriod() {
        return qualityGuaranteePeriod;
    }

    public void setQualityGuaranteePeriod(String qualityGuaranteePeriod) {
        this.qualityGuaranteePeriod = qualityGuaranteePeriod;
    }

    public QualityGuaranteePeriodType getQualityGuaranteePeriodType() {
        return qualityGuaranteePeriodType;
    }

    public void setQualityGuaranteePeriodType(QualityGuaranteePeriodType qualityGuaranteePeriodType) {
        this.qualityGuaranteePeriodType = qualityGuaranteePeriodType;
    }
}
