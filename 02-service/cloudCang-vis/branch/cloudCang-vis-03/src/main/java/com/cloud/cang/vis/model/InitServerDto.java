package com.cloud.cang.vis.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InitServerDto {

	private String modelPath; //模型地址

	private String nameCfg;//names配置文件地址

	@Builder.Default
	private int netIdx = 0;//可不赋值 默认值就行

	private int classes;//识别类别数量

	@Builder.Default
	private int anchorNum = 3;//可不赋值 默认值就行

	@Builder.Default
	private int istiny = 0;// 是否tiny模型 0否1是

	@Builder.Default
	private float thresh = 0.8f;// 置信度 默认0.8

	@Builder.Default
	private int isReloadModel = 1;// 是否重新初始化模型 0否1是
}
