package com.cloud.cang.recongition;


import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 视觉模型更新DTO
 * @author zhouhong
 * @version 1.0
 */
public class ModelUpdateDto extends SuperDto {
    /**
     * 更新记录编号或ID
     */
    private String updateRecodeCode;
    /**
     * 模型编号
     */
    private String modelCode;
    /**
     * 操作人
     */
    private String operMan;
    /**
     * 模型地址
     */
    private String modelAddress;
    /**
     * 模型地址类型  服务器本地 10  远程地址 20
     */
    private Integer addressType;
    /**
     * 更新视觉服务集合
     */
    private List<String> gpuCodes;

    public String getModelAddress() {
        return modelAddress;
    }

    public void setModelAddress(String modelAddress) {
        this.modelAddress = modelAddress;
    }

    public List<String> getGpuCodes() {
        return gpuCodes;
    }

    public void setGpuCodes(List<String> gpuCodes) {
        this.gpuCodes = gpuCodes;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getUpdateRecodeCode() {
        return updateRecodeCode;
    }

    public void setUpdateRecodeCode(String updateRecodeCode) {
        this.updateRecodeCode = updateRecodeCode;
    }

    public String getOperMan() {
        return operMan;
    }

    public void setOperMan(String operMan) {
        this.operMan = operMan;
    }

    @Override
    public String toString() {
        return "ModelUpdateDto{" +
                "updateRecodeCode='" + updateRecodeCode + '\'' +
                ", modelCode='" + modelCode + '\'' +
                ", operMan='" + operMan + '\'' +
                ", modelAddress='" + modelAddress + '\'' +
                ", addressType=" + addressType +
                ", gpuCodes=" + gpuCodes +
                '}';
    }
}
