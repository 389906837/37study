package com.cloud.cang.generatorMysql;

import java.io.IOException;

public class ModelMaker {

	private ParserInfo parserInfo;

	public ModelMaker(ParserInfo parserInfo) {
		this.parserInfo = parserInfo;
	}

	public void makeModel() throws IOException, Exception {
		Utils.writeContentToFile(
				Utils.createFile(parserInfo.getModelPackage(),
						parserInfo.getModelName(), ".java"),
				Utils.generateDaoContent(parserInfo, "ModelTemplateMysql.ftl"));
	}
}
