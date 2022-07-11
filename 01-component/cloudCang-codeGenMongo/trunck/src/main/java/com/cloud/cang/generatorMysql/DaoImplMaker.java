/**
 * 
 */
package com.cloud.cang.generatorMysql;

import java.io.IOException;

/**
 * @author hunter
 *
 */
public class DaoImplMaker {
	
	private ParserInfo parserInfo;

	public DaoImplMaker(ParserInfo parserInfo) {
		super();
		this.parserInfo = parserInfo;
	}
	
	public void makeModel() throws IOException, Exception {
		Utils.writeContentToFile(
				Utils.createFile(parserInfo.getDaoImplPackage(),
						parserInfo.getModelName(), "DaoImpl.java"),
				Utils.generateDaoContent(parserInfo, "DaoImplTemplateMongoDb.ftl"));
	}

}
