package com.cloud.cang.generatorMysql;

import java.io.IOException;
import java.util.Properties;

public class MongoDbGenerator {

	static Properties properties = new Properties();

	public static void execute() throws IOException, Exception {
		properties.load(ClassLoader.getSystemClassLoader()
				.getResourceAsStream("generator.properties"));
		String tables = properties.getProperty("tableName");
		String packageName = properties.getProperty("packageName");
		String packgePrefixName = properties.getProperty("packgePrefixName");
		String tableComment = properties.getProperty("tableComment");

		ParserInfo parserInfo = new ParserInfo(packageName+ "."+packgePrefixName, tables, "",
				properties.getProperty("entityPackageName") + "."
						+ packgePrefixName);
		parserInfo.setTableComment(tableComment);

		if (properties.getProperty("generatorTarget").indexOf("dao") != -1) {
			MapperMaker daoMaker = new MapperMaker(parserInfo);
			daoMaker.makeInterface();
			DaoImplMaker daoImplMaker = new DaoImplMaker(parserInfo);
			daoImplMaker.makeModel();
			
		}
		if (properties.getProperty("generatorTarget").indexOf("service") != -1) {
			ServiceInterfaceMaker serviceInterfaceMaker = new ServiceInterfaceMaker(
					parserInfo);
			serviceInterfaceMaker.makeModel();

			ServiceImplMaker serviceImplMaker = new ServiceImplMaker(parserInfo);
			serviceImplMaker.makeModel();
		}

		System.out.println("生成表   " + tables + " 成功...");
	}

}
