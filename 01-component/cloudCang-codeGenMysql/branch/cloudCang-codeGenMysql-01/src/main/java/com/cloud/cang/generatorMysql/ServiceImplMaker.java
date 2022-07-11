package com.cloud.cang.generatorMysql;

import java.io.IOException;

public class ServiceImplMaker {
	private ParserInfo parserInfo;
	public ServiceImplMaker(ParserInfo parserInfo) {
		this.parserInfo = parserInfo;
	}
	public void makeModel() throws Exception {
		Utils.writeContentToFile(
				Utils.createFile(parserInfo.getServiceImplPackage(),
						parserInfo.getModelName(), "ServiceImpl.java"),
				Utils.generateDaoContent(parserInfo, "ServiceTemplateImplMysql.ftl"));
	}
}
