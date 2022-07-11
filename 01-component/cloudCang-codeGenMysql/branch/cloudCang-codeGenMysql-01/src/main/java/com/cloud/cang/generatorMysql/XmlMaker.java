package com.cloud.cang.generatorMysql;

public class XmlMaker {
	private ParserInfo parserInfo;

	public XmlMaker(ParserInfo parserInfo) {
		this.parserInfo = parserInfo;
	}

	public void makeDao(String tableName) throws Exception {
		Utils.writeContentToFile(
				Utils.createFile(parserInfo.getDaoPackage(),
						parserInfo.getDaoName(), ".xml"),
				Utils.generateDaoContent(parserInfo, "TemplateMapperMysql.ftl"));
	}
}
