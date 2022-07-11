package com.cloud.cang.core.sys.service;


import com.cloud.cang.core.sys.domain.CodeGeneratorDomain;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sys.CodeGenerator;
import com.github.pagehelper.Page;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public interface CodeGeneratorService extends GenericService<CodeGenerator, Long> {
	
	
    void newCode(String scodeType, ConcurrentHashMap<String, BlockingQueue<String>> seqCodeCaches, ConcurrentHashMap<String, Map<String, Object>> queryDataDateCaches, BlockingQueue<String> codes);
    
    CodeGenerator selectCodeByTypeLocked(String stype);
    
    CodeGenerator selectCodeByType(String stype);
    
    void updateCodeByType(String stype);

    Page<CodeGenerator> selectByCode(Page<CodeGenerator> page, CodeGeneratorDomain domain);

    void batchUpdateDelFlag(String[] checkboxId, int delDisable);
 
    
}