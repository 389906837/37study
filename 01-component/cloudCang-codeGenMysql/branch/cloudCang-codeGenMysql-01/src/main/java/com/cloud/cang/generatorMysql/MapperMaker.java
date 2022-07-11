package com.cloud.cang.generatorMysql;

import java.io.IOException;


public class MapperMaker {

	private ParserInfo parserInfo;

	public MapperMaker(ParserInfo parserInfo) {
		this.parserInfo = parserInfo;
	}


	public void makeInterface() throws Exception {
		Utils.writeContentToFile(
				Utils.createFile(parserInfo.getDaoPackage(),
						parserInfo.getDaoName(), ".java"),
				Utils.generateDaoContent(parserInfo, "MapperTemplateMysql.ftl"));
		
	}

}
