package com.cloud.cang.core.sys.service.impl;

import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.exception.SeqCodeException;
import com.cloud.cang.core.sys.dao.CodeGeneratorDao;
import com.cloud.cang.core.sys.domain.CodeGeneratorDomain;
import com.cloud.cang.core.sys.service.CodeGeneratorService;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.sys.CodeGenerator;
import com.cloud.cang.utils.DateUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;


/**
 * code generator
 * 
 * @author zhouhong
 * @version 2.0
 *
 */
@Service
public class CodeGeneratorServiceImpl extends
		GenericServiceImpl<CodeGenerator, Long> implements CodeGeneratorService {

    private static Logger LOGGER = LoggerFactory
	    .getLogger(CodeGeneratorServiceImpl.class);

    @Autowired
    CodeGeneratorDao codeGeneratorDao;

    @Override
    public GenericDao<CodeGenerator, Long> getDao() {
	return codeGeneratorDao;
    }

    private void generatorCodeCaches(CodeGenerator currCodeResult,
	    BlockingQueue<String> codes) {

	for (int i = currCodeResult.getIcodeStart(); i < (currCodeResult
		.getIcodeStart() + currCodeResult.getIcodeStep()); i++) {

	    codes.add(generatorCode(currCodeResult, i));
	}
    }

    private String generatorCode(CodeGenerator currCodeResult, int size) {
	StringBuilder _stb = new StringBuilder(
		StringUtils.isEmpty(currCodeResult.getScodePrefix()) ? ""
			: currCodeResult.getScodePrefix());
	if (currCodeResult.getScodeDateformat() != null) {
	    _stb.append(getCurrentDate(currCodeResult.getScodeDateformat(),
		    new Date()));
	}
	String _i = size + "";
	int differ = currCodeResult.getIcodeSize() - _i.length();
	if (differ >= 1) {
	    for (int d = 0; d < differ; d++) {
		_stb.append("0");
	    }
	}
	_stb.append(_i);
	return _stb.toString();
    }

    private String getCurrentDate(String format, Date date) {
	try {
	    return new SimpleDateFormat(format).format(date);
	} catch (Exception e) {
	    return "";
	}
    }

    // get current date
    private Date getCurrentDate() {
	return DateUtils.getToday();
    }

    @Override
    public Page<CodeGenerator> selectByCode(Page<CodeGenerator> page,
											CodeGeneratorDomain domain) {
	PageHelper.startPage(page.getPageNum(), page.getPageSize());
	return codeGeneratorDao
		.selectByCode(domain);
    }

    @Override
    public void batchUpdateDelFlag(String[] checkboxId, int delDisable) {
	codeGeneratorDao.batchUpdateDelFlag(checkboxId, delDisable);
    }

    @Override
    public CodeGenerator selectCodeByTypeLocked(String stype) {
	return codeGeneratorDao.selectCodeByTypeLocked(stype);
    }

    @Override
    public void updateCodeByType(String stype) {
	codeGeneratorDao.updateCodeByType(stype);

    }

    @Override
    public void newCode(String scodeType,
	    ConcurrentHashMap<String, BlockingQueue<String>> seqCodeCaches,
	    ConcurrentHashMap<String, Map<String, Object>> queryDataDateCaches,
	    BlockingQueue<String> codes) {

	CodeGenerator currCodeResult = codeGeneratorDao
		.selectCodeByTypeLocked(scodeType);
	// cache queryDate
	Map<String, Object> cacheMap = queryDataDateCaches.get(scodeType);
	if (cacheMap == null) {
	    cacheMap = new HashMap<String, Object>();
	}
	cacheMap.put(CoreConstant.NEW_BEGIN, currCodeResult.getBanewBegin());
	cacheMap.put(CoreConstant.QUERY_DATE, getCurrentDate());
	queryDataDateCaches.put(scodeType, cacheMap);
	if (currCodeResult == null || currCodeResult.getIcodeStart() == null) {
	    throw new SeqCodeException("newcode,scodetype :" + scodeType
		    + " , config info is null or error");
	}
	if (currCodeResult.getIcodeStep() == null) {
	    currCodeResult.setIcodeStep(20);
	}
	try {
	    currCodeResult.setSlastCodeValue(generatorCode(
			currCodeResult,
			currCodeResult.getIcodeStart()
				+ currCodeResult.getIcodeStep() - 1));
	    generatorCodeCaches(currCodeResult, codes);
	    currCodeResult.setIcodeStart(currCodeResult.getIcodeStart()
		    + currCodeResult.getIcodeStep());
	    currCodeResult.setDcurrentDate(getCurrentDate());
	    this.updateBySelective(currCodeResult);

	} catch (Exception e) {
	    LOGGER.error("code create error,", e);

	}

    }

	@Override
	@Transactional(readOnly=true)
	public CodeGenerator selectCodeByType(String stype) {
		return codeGeneratorDao.selectCodeByType(stype);
	}

}