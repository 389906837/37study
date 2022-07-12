package com.cloud.cang.mgr.sys.web;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.ZKPConst;
import com.cloud.cang.core.utils.ZookpUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Zookeeper节点通知
 * @author zhouhong
 * @version 1.0
 */
@Controller
@RequestMapping("/zooSyn")
public class ZookeeperSynchronismController {
	@Autowired
	ZookpUtil zookpUtil;

	
	private String getFullCacheNotice(){
	 return	ZKPConst.CONFIG_PATH+ ZKPConst.CACHE_NOTICE ;
	}
	
	/**
	 * 省份城市缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheCity")
	public @ResponseBody
	ResponseVo cacheCity() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_CITY_INFO)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.city",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}

	/**
	 * 数据字典缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheDic")
	public @ResponseBody ResponseVo cacheDic() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_DATA_DICT)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.data.dic",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}

	/**
	 * 业务参数缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheBs")
	public @ResponseBody ResponseVo cacheBs() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_BS_PARAM)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.business.para",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}

	/**
	 * 节假日缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheHoliday")
	public @ResponseBody ResponseVo cacheHoliday() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_HOLIDAY_CONF)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.holiday",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}

	/**
	 * 页面关键字缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cachePageKeys")
	public @ResponseBody ResponseVo cachePageKeys() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice() + ZKPConst.CACHE_PAGE_KEYS)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.keywords",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}

	/**
	 * 后台权限码缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheMgrPurview")
	public @ResponseBody ResponseVo cachePurview() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_MGR_PURVIEW)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.purview",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
	
	/**
	 * 短信供应商缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheSupplier")
	public @ResponseBody ResponseVo cacheSupplier() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_SUPPLIERINFO)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.supplier",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
	
	/**
	 * 消息/协议模板缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheMsgTemplate")
	public @ResponseBody ResponseVo cacheMsgTemplate() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_MESSAGE_TEMPLATE)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.msg.template",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
	
	/**
	 * 节假日缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheHolidayConf")
	public @ResponseBody ResponseVo cacheHolidayConf() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_HOLIDAY_CONF)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.holiday",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
	
	/**
	 * 后台消息提醒缓存更新
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheRemindMessage")
	public @ResponseBody ResponseVo cacheRemindMessage() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_REMIND_MESSAGE)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.remind",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
	
	/**
	 * 商品类别缓存
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/cacheCategoryInfo")
	public @ResponseBody ResponseVo cacheCategoryInfo() {
		if (zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_CATEGORY_INFO) && zookpUtil.createrOrUpdate(getFullCacheNotice()+ ZKPConst.CACHE_HOT_CATEGORY_INFO)) {
			return ResponseVo.getSuccessResponse(MessageSourceUtils.getMessageByKey("syscon.zk.sync.category",null));
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM
					.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.zk.sync.error",null));
		}
	}
}
