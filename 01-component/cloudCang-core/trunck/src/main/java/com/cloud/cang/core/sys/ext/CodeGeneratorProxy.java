package com.cloud.cang.core.sys.ext;

import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.exception.SeqCodeException;
import com.cloud.cang.core.sys.service.CodeGeneratorService;
import com.cloud.cang.model.sys.CodeGenerator;
import com.cloud.cang.utils.DateUtils;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 编号生存代理服务
 * @author zhouhong
 * @version 1.0
 */
@Component
public class CodeGeneratorProxy {

    private static Logger LOGGER = LoggerFactory
	    .getLogger(CodeGeneratorProxy.class);

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    private ConcurrentHashMap<String, BlockingQueue<String>> seqCodeCaches = new ConcurrentHashMap<String, BlockingQueue<String>>();

    // cache query data date
    private ConcurrentHashMap<String, Map<String, Object>> queryDataDateCaches = new ConcurrentHashMap<String, Map<String, Object>>();

    // 弱类型引用
    private static Interner<String> interner = Interners.newWeakInterner();

    public String selectCode(String codeType) {

	if (StringUtils.isEmpty(codeType)) {
	    throw new SeqCodeException("newcode -> scodeType is null");
	}
	synchronized (interner.intern(codeType)) {

	    BlockingQueue<String> codesQueue = seqCodeCaches.get(codeType);
	    if (codesQueue == null) {
		codesQueue = new LinkedBlockingQueue<String>();
	    }
	    seqCodeCaches.put(codeType, codesQueue);

	    // 是否重新生成
	    Integer reBegin = 0;

	    Map<String, Object> cacheMap1 = queryDataDateCaches.get(codeType);
	    if (cacheMap1 == null) {
		cacheMap1 = new HashMap<String, Object>();
	    }
	    if (cacheMap1 == null
		    || cacheMap1.get(CoreConstant.QUERY_DATE) == null
		    || cacheMap1.get(CoreConstant.NEW_BEGIN) == null) {
		// query datebase
		CodeGenerator codeGenerator = codeGeneratorService
			.selectCodeByType(codeType);
		reBegin = codeGenerator.getBanewBegin() == null ? 0
			: codeGenerator.getBanewBegin();
		cacheMap1.put(CoreConstant.NEW_BEGIN, reBegin);
		cacheMap1.put(CoreConstant.QUERY_DATE,
			codeGenerator.getDcurrentDate());
		queryDataDateCaches.put(codeType, cacheMap1);

	    }

	    Map<String, Object> cacheMapbefore = queryDataDateCaches
		    .get(codeType);
	    // 清除缓存
	    Date dateBaseDate = cacheMapbefore.get(CoreConstant.QUERY_DATE) != null ? (Date) cacheMapbefore
		    .get(CoreConstant.QUERY_DATE) : null;
	    reBegin = cacheMapbefore.get(CoreConstant.NEW_BEGIN) != null ? (Integer) cacheMapbefore
		    .get(CoreConstant.NEW_BEGIN) : 0;
	    if (reBegin == 1
		    && dateBaseDate != null
		    && DateUtils.truncate(dateBaseDate, Calendar.DATE)
			    .getTime() < getCurrentDate().getTime()) {
		// 清除缓存
		seqCodeCaches.get(codeType).clear();
		codeGeneratorService.updateCodeByType(codeType);
	    }

	    BlockingQueue<String> codes = seqCodeCaches.get(codeType);
	    if (codes == null || codes.isEmpty()) {
		codeGeneratorService.newCode(codeType, seqCodeCaches,
			queryDataDateCaches, codes);
	    }

	    return codes.remove();

	}
    }

    // get current date
    private Date getCurrentDate() {
	return DateUtils.getToday();
    }

}
