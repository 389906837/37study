package com.cloud.cang.core.sys.dao;


import com.cloud.cang.core.sys.domain.CodeGeneratorDomain;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.CodeGenerator;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;


/** 编号生成配置表(SYS_CODE_GENERATOR) **/
public interface CodeGeneratorDao extends GenericDao<CodeGenerator, Long> {
	
   CodeGenerator selectCodeByTypeLocked(String stype);
   
   CodeGenerator selectCodeByType(String stype);
   
   void updateCodeByType(String stype);
   
   void batchUpdateDelFlag(@Param("idItems") String[] idItems, @Param("delFlag") int delDisable);

    Page<CodeGenerator> selectByCode(CodeGeneratorDomain domain);

}