package com.cloud.cang.generatorMysql;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class Generator {
	public static Properties properties = new Properties();

	public static void getPropertiesFileList(String path,List<String> fileList) {
		if (path.charAt(path.length() - 1) != '\\' && !Objects.equals(path.substring(path.length() - 1, path.length()), File.separator)) {
			path += '\\';
		}
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("Error: Path not Existed! Please Check it out!");
		}
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			File temp = new File(path + filelist[i]);
			if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {
				getPropertiesFileList(path + filelist[i],fileList);
			} else {
				if (filelist[i].equals("generator.properties")) {
					fileList.add(temp.getAbsolutePath());
				}
			}
		}
	}


	private static Connection getConnection() throws IOException, ClassNotFoundException, SQLException {

		String resourcePath=null;
		String currrntPath=null;
		String targetPath=Class.class.getClass().getResource("/").getPath();
		if(targetPath.contains("/target/test-classes")){
			List<String> fileList=new ArrayList<>();
			currrntPath= java.net.URLDecoder.decode(targetPath.substring(0,targetPath.length()-20),"utf-8");
			getPropertiesFileList(currrntPath+"src"+ File.separator+"test",fileList);
			if(fileList.size()==1){
				resourcePath = fileList.get(0);
				System.out.println("加载资源文件:"+resourcePath);
			}else if(fileList.size()>1){

				for(String s:fileList){
					System.out.println("资源文件路径:"+s);
				}
				throw new RuntimeException("存在多个资源文件，请只留一个！");
			}else{
				throw new RuntimeException("没有找到资源文件");
			}

		}
		InputStream is = new FileInputStream(resourcePath);
		properties.load(is);

		String driverClass = properties.getProperty("mysql.jdbc.driverClass");
		String url = properties.getProperty("mysql.jdbc.url");
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(url, properties.getProperty("mysql.user"), properties.getProperty("mysql.password"));//.getConnection(url, properties);
		if (connection == null) {
			throw new RuntimeException("connection is null, Please check the driver settings!");
		}
		return connection;
	}

	/**
	 * 读取table信息
	 *
	 * @author cary
	 * @return
	 * @throws IOException
	 */
	private static List<TableEntry> readTableEntries() throws IOException {
		String tables=properties.getProperty("tableName");
		List<TableEntry> entries =new ArrayList<TableEntry>();
		for(String table:tables.split(",")){
			TableEntry te=new TableEntry();
			te.setTableName(table);
			te.setPackageName(properties.getProperty("packageName")+"."+ Utils.getSubPackage(table));
			te.setPrefixName(Utils.getSubPackage(table));
			entries.add(te);
		}
		return entries;
	}



	public static void execute() throws Exception {
		Connection connection = getConnection();
		List<TableEntry> entries = readTableEntries();
		System.out.println("There is(are) " + entries.size() + " table(s) to generate!");
		for (TableEntry entry : entries) {
			String packageName = entry.getPackageName();
			String tableName = entry.getTableName();
			String sequenceName = entry.getSequenceName();
			tableName = tableName.toUpperCase();
			if (sequenceName != null) {
				sequenceName = sequenceName.toUpperCase();
			}

			String tableSchema = properties.getProperty("mysql.tableSchema");
			if(properties.getProperty("generatorTarget").indexOf("po")!=-1){
				String modelPackageName = "com.cloud.cang.model."+entry.getPrefixName();
				ParserInfo parserInfo = new ParserInfo(connection, modelPackageName, tableName, sequenceName, tableSchema);
				ModelMaker modelMaker = new ModelMaker(parserInfo);
				modelMaker.makeModel();
			}

			ParserInfo parserInfo = new ParserInfo(connection, packageName, tableName, sequenceName,properties.getProperty("entityPackageName")+"."+entry.getPrefixName(),tableSchema);

			if(properties.getProperty("generatorTarget").indexOf("xml")!=-1){
				XmlMaker xmlMaker=new XmlMaker(parserInfo);
				xmlMaker.makeDao(tableName);
			}

			if(properties.getProperty("generatorTarget").indexOf("dao")!=-1){
				MapperMaker daoMaker = new MapperMaker(parserInfo);
				daoMaker.makeInterface();
            }
			if(properties.getProperty("generatorTarget").indexOf("service")!=-1){
				ServiceInterfaceMaker serviceInterfaceMaker = new ServiceInterfaceMaker(parserInfo);
				serviceInterfaceMaker.makeModel();

				ServiceImplMaker serviceImplMaker = new ServiceImplMaker(parserInfo);
				serviceImplMaker.makeModel();
			}
			//ModelMaker modelMaker = new ModelMaker(parserInfo);
			//modelMaker.makeModel();




			//DaoInterfaceMaker daoInterfaceMaker = new DaoInterfaceMaker(parserInfo);
			//daoInterfaceMaker.makeInterface();

			//DaoModelMaker daoModelMaker = new DaoModelMaker(parserInfo);
			//daoModelMaker.makeModel();
			System.out.println("生成表   " + tableName + " 成功...");
		}
		connection.close();
	}
}
