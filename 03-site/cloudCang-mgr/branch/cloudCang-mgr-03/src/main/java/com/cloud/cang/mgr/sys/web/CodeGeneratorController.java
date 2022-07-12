package com.cloud.cang.mgr.sys.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.domain.CodeGeneratorDomain;
import com.cloud.cang.core.sys.service.CodeGeneratorService;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.model.sys.CodeGenerator;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;


/**
 * 
 * 编号生成配置控制层
 * 
 * @version 1.0
 * 
 */
@Controller
@RequestMapping("/codeGenerator")
public class CodeGeneratorController {

	@Autowired
	CodeGeneratorService codeGeneratorService;
	private static final Logger log = LoggerFactory.getLogger(CodeGeneratorController.class);

	/**可用*/
	public static final int DEL_FALSE=0;
	
	/**已删除*/
	public static final int DEL_TRUE=1;
	
	/**
     * 进入编号生成配置
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sys/codeGenerator/codeGenerator-list";
    }
    
    /**
     * 查询编号生成配置
     * @return
     */
    @RequestMapping("/query")
    public @ResponseBody PageListVo<List<CodeGenerator>> query(ParamPage paramPage, CodeGeneratorDomain domain) {
		PageListVo<List<CodeGenerator>> pageListVo = new PageListVo<List<CodeGenerator>>();

    	Page<CodeGenerator> page = new Page<CodeGenerator>();
		page.setPageNum(paramPage.getPage());
		page.setPageSize(paramPage.getRows());
		domain.setIdelFlag(DEL_FALSE);
		if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
			domain.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
		}
    	page = codeGeneratorService.selectByCode(page, domain);
		if (page != null) {
			pageListVo.setPage(page.getPageNum());
			pageListVo.setRecords(page.getTotal());
			pageListVo.setTotal(page.getPages());
			pageListVo.setRows(page.getResult());
		}
		return pageListVo;
    }

	@RequestMapping("/edit")
	public String edit(ModelMap map, Long sid) {
    	try{
		CodeGenerator codeGenerator = codeGeneratorService.selectByPrimaryKey(sid);
		if (codeGenerator == null) {
			codeGenerator = new CodeGenerator();
			map.put("isAdd", 1);
		}
		map.put("codeGenerator" , codeGenerator);
		return "sys/codeGenerator/codeGenerator-edit";
		} catch (Exception e) {
			log.error("跳转页面异常：{}", e);
		}
		return ExceptionUtil.to500();
	}
    /**
     * 批量删除编号生成配置
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody ResponseVo delete(String[] checkboxId) {
    	codeGeneratorService.batchUpdateDelFlag(checkboxId,DEL_TRUE);
		// 操作日志
		String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.delete.code.generator",null)+"{0}", checkboxId);
		LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
        return ResponseVo.getSuccessResponse();
    }
    
    /**
     * 新增编号生成配置
     * @return
     */
    @RequestMapping("/insert")
    public @ResponseBody ResponseVo insert(CodeGenerator codeGeneratorConfig) {
    	codeGeneratorConfig.setIdelFlag(DEL_FALSE);
    	
    	CodeGenerator condition=new CodeGenerator();
    	condition.setScodeType(codeGeneratorConfig.getScodeType());
    	List<CodeGenerator> list=codeGeneratorService.selectByEntityWhere(condition);
    	
    	if(list!=null&&list.size()>0){
    		CodeGenerator generatorConfig=list.get(0);
    		if(DEL_FALSE==generatorConfig.getIdelFlag()){
    			return ErrorCodeEnum.ERROR_COMMON_UNIQUE.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.add.code.generator.error",null));
    		}else{
    			codeGeneratorService.updateBySelective(codeGeneratorConfig);
    		}
        	
    	}
    	if (codeGeneratorConfig.getBanewBegin()==null)codeGeneratorConfig.setBanewBegin(0);
    	codeGeneratorService.insert(codeGeneratorConfig);
		//操作日志
		String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.add.code.generator",null)+"，"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", codeGeneratorConfig.getScodeType());
		LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        return ResponseVo.getSuccessResponse();
    }
    
    /**
     * 修改编号生成配置
     * @return
     */
    @RequestMapping("/update")
    public @ResponseBody ResponseVo update(CodeGenerator codeGeneratorConfig) {
    	codeGeneratorConfig.setIdelFlag(DEL_FALSE);
    	codeGeneratorService.updateByPrimaryKey(codeGeneratorConfig);
		//操作日志
		String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.modify.code.generator",null)+"，"+MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", codeGeneratorConfig.getScodeType());
		LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }
    
}
