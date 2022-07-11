package com.cloud.cang.generatorMysql;

import java.io.IOException;

public class ServiceInterfaceMaker {

	private ParserInfo parserInfo;

	public ServiceInterfaceMaker(ParserInfo parserInfo) {
		this.parserInfo = parserInfo;
	}

	public void makeModel() throws Exception {
		Utils.writeContentToFile(
				Utils.createFile(
						parserInfo.getServicePackage(),parserInfo.getModelName(), "Service.java"),
				Utils.generateDaoContent(parserInfo, "ServiceTemplateMysql.ftl"));
	}
}
