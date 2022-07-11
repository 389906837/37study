package com.cloud.cang.api.antbox.dto;

import com.cloud.cang.api.antbox.constant.InventoryType;

import java.util.Set;
import java.util.UUID;

/**
 * Created by oyhk on 2017/9/11.
 */
public class InventoryConclusionDto {

    // 消息id 生成策略使用uuid
    private String msgId = UUID.randomUUID().toString();
    // 售货机id
    private Long boxId;
    // 当前购物用户
    private CustomerDto currentCustomer;
    // 最后购物用户
    private CustomerDto lastCustomer;
    // 预期盘点时间
    private Integer expectedInventoryTimes;
    // 实际盘点时间
    private Integer actualInventoryTimes;
    // 同一次盘点的轮次开始时间
    private Long elapsedMills;
    // 盘点类型
    private InventoryType inventoryType;
    // rfid 差集
    private Set<String> lossLabels;
    // rfid 差集
    private Set<String> newLabels;
    // rfid 所有
    private Set<String> currentInventoryLabels;
    // 日志跟踪id
    private String traceId;


    public CustomerDto getCurrentCustomer() {
        return currentCustomer;
    }

    public void setCurrentCustomer(CustomerDto currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    public CustomerDto getLastCustomer() {
        return lastCustomer;
    }

    public void setLastCustomer(CustomerDto lastCustomer) {
        this.lastCustomer = lastCustomer;
    }

    public Integer getExpectedInventoryTimes() {
        return expectedInventoryTimes;
    }

    public void setExpectedInventoryTimes(Integer expectedInventoryTimes) {
        this.expectedInventoryTimes = expectedInventoryTimes;
    }

    public Integer getActualInventoryTimes() {
        return actualInventoryTimes;
    }

    public void setActualInventoryTimes(Integer actualInventoryTimes) {
        this.actualInventoryTimes = actualInventoryTimes;
    }

    public Long getElapsedMills() {
        return elapsedMills;
    }

    public void setElapsedMills(Long elapsedMills) {
        this.elapsedMills = elapsedMills;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public Set<String> getLossLabels() {
        return lossLabels;
    }

    public void setLossLabels(Set<String> lossLabels) {
        this.lossLabels = lossLabels;
    }

    public Set<String> getNewLabels() {
        return newLabels;
    }

    public void setNewLabels(Set<String> newLabels) {
        this.newLabels = newLabels;
    }

    public Set<String> getCurrentInventoryLabels() {
        return currentInventoryLabels;
    }

    public void setCurrentInventoryLabels(Set<String> currentInventoryLabels) {
        this.currentInventoryLabels = currentInventoryLabels;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }
}
