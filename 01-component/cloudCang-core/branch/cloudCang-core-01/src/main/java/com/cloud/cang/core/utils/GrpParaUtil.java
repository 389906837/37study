package com.cloud.cang.core.utils;


import com.cloud.cang.core.sys.domain.DataDicDomain;
import com.cloud.cang.core.sys.service.CacheDataDicService;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.SpringContext;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;



/**
 * 缓存数据字典常用类
 * @version 1.0
 */
public class GrpParaUtil {

    /**系统参数字典项CODE*/
    public static final String SYSTEM_PARA_CODE = "SP000000";
    /**上传图片路径配置*/
    public static final String FTP_ARTICE_ADDRESS = "ftpImagePath";
	/**FTP配置*/
	public static final String FTP_CONFIG = "SP000001";
	/**默认商户配置*/
	public static final String DEFAULT_MERCHANT_CONFIG = "SP000106";
	/**会员来源配置*/
	public static final String MEMBER_SOURCE = "SP000037";
	/**注册活动*/
	public static final String REGISTER_ACTIVITY = "SP000092";


	/**长连接配置**/
	public static final String TCP_CONFIG = "SP000141";

    /**售货机设备广告运营位置配置**/
	public static final String OPERATE_ADVERTISING_CONFIG = "SP000148";

    /**设备区域**/
	public static final String DEVICE_OPERATE_AREA = "SP000145";

    /**商品主类**/
	public static final String MAIN_COMMODITY_CATEGORY = "SP000136";


	/**设备负责人区域**/
	public static final String DEVICE_MANAGER_AREA = "SP000145";


	/**商品标识类型**/
	public static final String COMMODITY_MARK_TYPE = "SP000153";


    /**更新各种服务地址前缀配置**/
    public static final String UPDATE_URL_PREX_CONFIG = "SP000155";

    /**人脸注册相关参数配置**/
    public static final String AI_FACE_CONFIG = "SP000156";

    /**云端识别相关参数配置**/
    public static final String CLOUD_RECOGNITION_OPEN_SDK = "SP000166";

    /**年会人脸识别相关参数配置**/
    public static final String AM_FACE_CONFIG = "SP000167";


    /**摄像头类型_品牌_型号_使用方法配置**/
    public static final String DEVICE_CAMERA_CONFIG = "SP000171";




	private static CacheDataDicService service=null;


	/**
	 * 在缓存中根据key返回集合参数表中配置值
	 *
	 * @param groupCode
	 * @return
	 */
	public static Set<ParameterGroupDetail> get(String groupCode) {
		if (StringUtils.isBlank(groupCode)) return null;
		return getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
	}
	/**
	 * 在缓存中根据key返回集合参数表中配置值
	 * @param groupCode
	 * @return
	 */
	public static DataDicDomain getMain(String groupCode) {
		if (StringUtils.isBlank(groupCode)) return null;
		return getService().selectCacheDataDictInfo(groupCode);
	}
	
	

	/**
	 * 在缓存中根据主项编号和子项值查找name
	 */
	public static String getName(String groupCode, String value) {
		if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(value)) return "";
        Set<ParameterGroupDetail> _vs = getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
        ParameterGroupDetail _dest = new ParameterGroupDetail();
        try {
            for (ParameterGroupDetail _d : _vs) {
                if (value.equalsIgnoreCase(_d.getSvalue())) {
                    BeanUtils.copyProperties(_dest, _d);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (_dest == null)
            return "";
        return _dest.getSname();
	}

	/**
     * 在缓存中根据主项编号和子项值查找说明
     */
    public static String getExplainForValue(String groupCode, String value) {
    	if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(value)) return "";
        Set<ParameterGroupDetail> _vs = getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
        ParameterGroupDetail _dest = new ParameterGroupDetail();
        try {
            for (ParameterGroupDetail _d : _vs) {
                if (value.equalsIgnoreCase(_d.getSvalue())) {
                    BeanUtils.copyProperties(_dest, _d);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (_dest == null)
            return "";
        return _dest.getSremark();
    }
    
	/**
     * 在缓存中根据主项编号和子项名称查出详情
     */
    public static ParameterGroupDetail getDetailForName(String groupCode, String name) {
    	if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(name)) return null;
        Set<ParameterGroupDetail> _vs = getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
        ParameterGroupDetail _dest = new ParameterGroupDetail();
        try {
            for (ParameterGroupDetail _d : _vs) {
                if (name.equalsIgnoreCase(_d.getSname())) {
                    BeanUtils.copyProperties(_dest, _d);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _dest;
    }
    
	/**
     * 在缓存中根据主项编号和子项值查出详情
     */
    public static ParameterGroupDetail getDetailForValue(String groupCode, String value) {
    	if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(value)) return null;
        Set<ParameterGroupDetail> _vs = getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
        ParameterGroupDetail _dest = new ParameterGroupDetail();
        try {
            for (ParameterGroupDetail _d : _vs) {
                if (value.equalsIgnoreCase(_d.getSvalue())) {
                    BeanUtils.copyProperties(_dest, _d);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _dest;
    }
    
	/**
	 * 在缓存中根据主项编号和子项名查找说明
	 */
    public static String getExplain(String groupCode, String name) {
    	if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(name)) return "";
        Set<ParameterGroupDetail> _vs = getService().selectCacheDataDictInfo(groupCode).getDataDicDetails();
        ParameterGroupDetail _dest = new ParameterGroupDetail();
        try {
            for (ParameterGroupDetail _d : _vs) {
                if (name.equalsIgnoreCase(_d.getSname())) {
                    BeanUtils.copyProperties(_dest, _d);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (_dest == null)
            return "";
        return _dest.getSremark();
    }

	/**
	 * 在缓存中根据关键字和名字查找值
	 */
	public static String getValue(String groupCode, String name) {
		if (StringUtils.isBlank(groupCode) || StringUtils.isBlank(name)) return "";
		ParameterGroupDetail dtl= getService().selectCacheParamGroupDetailInfo(groupCode, name);
		if(dtl==null)return "";
		return dtl.getSvalue();
	}

	/***
	 * 初始化Bean
	 */
	private static CacheDataDicService getService() {
		if (service == null) {
			service = SpringContext.getBean("dataDicServiceImpl");
		}
		return service;
	}
}
