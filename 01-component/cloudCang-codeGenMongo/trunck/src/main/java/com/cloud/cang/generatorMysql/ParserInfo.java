package com.cloud.cang.generatorMysql;

import java.util.ArrayList;
import java.util.List;

public class ParserInfo {

	// #<tableName>
	private String tableName;
	// #<tableComment>
	private String tableComment;
	// #<sequenceName>
	private String sequenceName;
	// #<daoPackage>
	private String daoPackage;
	// #<daoName>
	private String daoName;
	private String daoVarName;
	private String daoImplPackage;
	
	
	// #<modelPackage>
	private String modelPackage;
	// #<modelName>
	private String modelName;
	
	private String servicePackage;
	private String serviceImplPackage;

	private String keyColName;
	
	private String subPackage;
	
	private List<Field> fields;
	
	private Field primarykeyFields;

	public ParserInfo(String parentPackageName, String tableName, String sequenceName)
			throws Exception {
		fields = new ArrayList<Field>();
		daoPackage = parentPackageName +".dao";
		daoImplPackage = parentPackageName+".dao.impl";
		servicePackage= parentPackageName +".service";
		serviceImplPackage= parentPackageName +".service.impl";
		//modelPackage = parentPackageName;
		modelPackage = parentPackageName+ "";
		this.tableName = tableName;
		if (sequenceName == null) {
			sequenceName = tableName + "_S";
		}
		this.sequenceName = sequenceName;
		String javaName = Utils.getJavaName(tableName);
		daoName = javaName + "Dao";
		daoVarName=daoName.substring(0,1).toLowerCase()+daoName.substring(1);
		modelName = javaName;
		
	
	}

	public ParserInfo(String parentPackageName, String tableName, String sequenceName,String modelPackageName)
			throws Exception {
		fields = new ArrayList<Field>();
		daoPackage = parentPackageName +".dao";
		daoImplPackage = parentPackageName+".dao.impl";
		servicePackage= parentPackageName +".service";
		serviceImplPackage= parentPackageName +".service.impl";
		//modelPackage = parentPackageName;
		modelPackage = modelPackageName+ "";
		this.tableName = tableName;
		if (sequenceName == null) {
			sequenceName = tableName + "_S";
		}
		this.sequenceName = sequenceName;
		String javaName = Utils.getJavaName(tableName);
		daoName = javaName + "Dao";
		daoVarName=daoName.substring(0,1).toLowerCase()+daoName.substring(1);
		modelName = javaName;
		
		
	}


	public String getDaoName() {
		return daoName;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public List<Field> getFields() {
		return fields;
	}

	

	public String getModelName() {
		return modelName;
	}

	public String getModelPackage() {
		return modelPackage;
	}

	

	public String getSequenceName() {
		return sequenceName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public String getTableName() {
		return tableName;
	}

	public void setDaoName(String daoName) {
		this.daoName = daoName;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public void setModelPackage(String modelPackage) {
		this.modelPackage = modelPackage;
	}

	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getKeyColName() {
		return keyColName;
	}

	public void setKeyColName(String keyColName) {
		this.keyColName = keyColName;
	}

	public String getSubPackage() {
		return subPackage;
	}

	public void setSubPackage(String subPackage) {
		this.subPackage = subPackage;
	}

	public Field getPrimarykeyFields() {
		return primarykeyFields;
	}

	public void setPrimarykeyFields(Field primarykeyFields) {
		this.primarykeyFields = primarykeyFields;
	}

	public String getServicePackage() {
		return servicePackage;
	}

	public void setServicePackage(String servicePackage) {
		this.servicePackage = servicePackage;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getDaoVarName() {
		return daoVarName;
	}

	public void setDaoVarName(String daoVarName) {
		this.daoVarName = daoVarName;
	}

	public String getDaoImplPackage() {
		return daoImplPackage;
	}

	public void setDaoImplPackage(String daoImplPackage) {
		this.daoImplPackage = daoImplPackage;
	}
	
}
