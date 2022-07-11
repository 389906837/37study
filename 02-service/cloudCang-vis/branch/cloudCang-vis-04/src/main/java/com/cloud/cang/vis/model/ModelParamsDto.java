package com.cloud.cang.vis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelParamsDto {

    private String weightPath; //模型地址 37.weights

    private String configPath;//配置文件地址 37.detect.cfg

    private String classesPath;//37.names

    private int classes;//识别类别数量

    @Builder.Default
    private int gpuIndex = 0;//识别GPU序号

    @Builder.Default
    private float thresh = 0.8f;// 置信度 默认0.8

    @Builder.Default
    private int isReloadModel = 1;// 是否重新初始化模型 0否1是
}
